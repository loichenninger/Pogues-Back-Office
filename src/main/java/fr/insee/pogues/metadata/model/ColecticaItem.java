package fr.insee.pogues.metadata.model;


import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class ColecticaItem {

	@Schema(description = "ItemType")
    @JsonProperty("ItemType")
    public String itemType;

	@Schema(description = "AgencyId")
    @JsonProperty("AgencyId")
    public String agencyId;

	@Schema(description = "Version")
    @JsonProperty("Version")
    public String version;

	@Schema(description = "Identifier")
    @JsonProperty("Identifier")
    public String identifier;

	@Schema(description = "DDI representation of the item")
    @JsonProperty("Item")
    public String item;

	@Schema(description = "VersionDate")
    @JsonProperty("VersionDate")
    public String versionDate;

	@Schema(description = "VersionResponsibility")
    @JsonProperty("VersionResponsibility")
    public String versionResponsibility;

	@Schema(description = "IsPublished")
    @JsonProperty("IsPublished")
    public boolean isPublished;

	@Schema(description = "IsDeprecated")
    @JsonProperty("IsDeprecated")
    public boolean isDeprecated;

	@Schema(description = "IsProvisional")
    @JsonProperty("IsProvisional")
    public boolean isProvisional;

	@Schema(description = "ItemFormat")
    @JsonProperty("ItemFormat")
    public String itemFormat;

	@Schema(description = "VersionRationale")
    @JsonProperty("VersionRationale")
    public JSONObject versionRationale;

	@Schema(description = "Notes")
    @JsonProperty("Notes")
    public JSONArray notes;

    /////////


    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public String getAgencyId() {
        return agencyId;
    }

    public void setAgencyId(String agencyId) {
        this.agencyId = agencyId;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getVersionDate() {
        return versionDate;
    }

    public void setVersionDate(String versionDate) {
        this.versionDate = versionDate;
    }

    public String getVersionResponsibility() {
        return versionResponsibility;
    }

    public void setVersionResponsibility(String versionResponsibility) {
        this.versionResponsibility = versionResponsibility;
    }

    public boolean isPublished() {
        return isPublished;
    }

    public void setPublished(boolean published) {
        isPublished = published;
    }

    public boolean isDeprecated() {
        return isDeprecated;
    }

    public void setDeprecated(boolean deprecated) {
        isDeprecated = deprecated;
    }

    public boolean isProvisional() {
        return isProvisional;
    }

    public void setProvisional(boolean provisional) {
        isProvisional = provisional;
    }

    public String getItemFormat() {
        return itemFormat;
    }

    public void setItemFormat(String itemFormat) {
        this.itemFormat = itemFormat;
    }

    public JSONObject getVersionRationale() {
        return versionRationale;
    }

    public void setVersionRationale(JSONObject versionRationale) {
        this.versionRationale = versionRationale;
    }

    public JSONArray getNotes() {
        return notes;
    }

    public void setNotes(JSONArray notes) {
        this.notes = notes;
    }
}
