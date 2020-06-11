package fr.insee.pogues.config.auth.roles;

import fr.insee.pogues.exceptions.PoguesException;

public interface UserRolesManagerService {
	
	public String getAuth(String body);
	
	public String getRoles() throws PoguesException;
	
	public String getAgents() throws PoguesException;
	
	public void setAddRole(String role, String user);
	
	public void setDeleteRole(String roles, String user);
	
}
