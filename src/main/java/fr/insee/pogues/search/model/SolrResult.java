package fr.insee.pogues.search.model;

import java.util.Arrays;

public class SolrResult {
	
	private String id;
	
	private String label;
	
	private String description;
	
	private String type;
	
	private int version;
	
	private String[] modalities;
	
	private String[] subGroups;
	
	private String[] subgroupLabels;
	
	private String[] datacollections;
	
	private String[] studyUnits;
	
	public SolrResult() {
		super();
	}

	public SolrResult(String id, String label, String description, String type, int version, String[] modalities,
			String[] subGroups, String[] subgroupLabels, String[] datacollections, String[] studyUnits) {
		super();
		this.id = id;
		this.label = label;
		this.description = description;
		this.type = type;
		this.version = version;
		this.modalities = modalities;
		this.subGroups = subGroups;
		this.subgroupLabels = subgroupLabels;
		this.datacollections = datacollections;
		this.studyUnits = studyUnits;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public String[] getModalities() {
		return modalities;
	}

	public void setModalities(String[] modalities) {
		this.modalities = modalities;
	}

	public String[] getSubGroups() {
		return subGroups;
	}

	public void setSubGroups(String[] subGroups) {
		this.subGroups = subGroups;
	}

	public String[] getSubgroupLabels() {
		return subgroupLabels;
	}

	public void setSubgroupLabels(String[] subgroupLabels) {
		this.subgroupLabels = subgroupLabels;
	}

	public String[] getDatacollections() {
		return datacollections;
	}

	public void setDatacollections(String[] datacollections) {
		this.datacollections = datacollections;
	}

	public String[] getStudyUnits() {
		return studyUnits;
	}

	public void setStudyUnits(String[] studyUnits) {
		this.studyUnits = studyUnits;
	}

	@Override
	public String toString() {
		return "SolrResult [id=" + id + ", label=" + label + ", description=" + description + ", type=" + type
				+ ", version=" + version + ", modalities=" + Arrays.toString(modalities) + ", subGroups="
				+ Arrays.toString(subGroups) + ", subgroupLabels=" + Arrays.toString(subgroupLabels)
				+ ", datacollections=" + Arrays.toString(datacollections) + ", studyUnits="
				+ Arrays.toString(studyUnits) + "]";
	}
	
}
