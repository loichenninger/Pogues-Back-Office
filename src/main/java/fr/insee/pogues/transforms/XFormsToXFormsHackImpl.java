package fr.insee.pogues.transforms;

import net.sf.saxon.s9api.*;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * Created by bwerquin on 20/07/17.
 * TODO Register as a provider
 */
@Service
public class XFormsToXFormsHackImpl implements XFormsToXFormsHack {

    private final String XSLT_FILE_0 = "transforms/xslt/xformsToXformsHack.xsl";
    private final String XSLT_FILE_1 = "transforms/xslt/xformsToXformsHack2.xsl";


    private Logger logger = LogManager.getLogger(XFormsToXFormsHackImpl.class);

    public void transform(InputStream input, OutputStream output, Map<String, Object>params) throws Exception {
        if (null == input) {
            throw new NullPointerException("Null input");
        }
        if (null == output) {
            throw new NullPointerException("Null output");
        }
        try {
            Processor processor = new Processor(false);
            Source source = new StreamSource(input);
            XsltTransformer t = createPipeline(source, output, processor);
            t.transform();
        } catch (SaxonApiException e) {
            throw new Exception(String.format("%s:%s, Line: %d, Error Code: %s",
                    getClass().getName(), e.getMessage(), e.getLineNumber(), e.getErrorCode()));
        }
    }

    public String transform(InputStream input, Map<String, Object>params) throws Exception {
        if (null == input) {
            throw new NullPointerException("Null input");
        }
        try ( ByteArrayOutputStream output = new ByteArrayOutputStream()){
            transform(input, output, params);
            return output.toString(StandardCharsets.UTF_8).trim();
        }
    }

    public String transform(String input, Map<String, Object>params) throws Exception {
        if (null == input) {
            throw new NullPointerException("Null input");
        }
        try (InputStream is = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8))){
            return transform(is, params);
        }
    }

    private XsltTransformer createPipeline(Source input, OutputStream output, Processor processor) throws Exception {
        DocumentBuilder builder = processor.newDocumentBuilder();
        XdmNode source = builder.build(input);
        XsltTransformer t0 = createTransformer(processor, XSLT_FILE_0);
        XsltTransformer t1 = createTransformer(processor, XSLT_FILE_1);
        Serializer out = createSerializer(processor, output);
        t0.setInitialContextNode(source);
        t0.setDestination(t1);
        t1.setDestination(out);
        return t0;
    }

    private XsltTransformer createTransformer(Processor processor, String path) throws SaxonApiException {
        XsltCompiler compiler = processor.newXsltCompiler();
        InputStream xslResource = getClass().getClassLoader().getResourceAsStream(path);
        if (null == xslResource) {
            throw new NullPointerException("NULL XSLT Resource");
        }
        XsltExecutable xsl = compiler.compile(new StreamSource(xslResource));
        XsltTransformer transformer = xsl.load();
        transformer.setSchemaValidationMode(ValidationMode.LAX);
        transformer.setErrorListener(new PoguesErrorListener());
        return transformer;
    }

    private Serializer createSerializer(Processor processor, OutputStream output) {
        Serializer out = processor.newSerializer(output);
        out.setOutputProperty(Serializer.Property.SAXON_STYLESHEET_VERSION, "2.0");
        out.setOutputProperty(Serializer.Property.METHOD, "xml");
        out.setOutputProperty(Serializer.Property.INDENT, "yes");
        out.setOutputProperty(Serializer.Property.OMIT_XML_DECLARATION, "yes");
        out.setOutputProperty(Serializer.Property.ENCODING, "utf-8");
        return out;
    }

}
