package org.crudservlet.dto;

import java.util.Set;

public class GetPermissionsResponseDTO {

    private Set<String> Permissions;

    public GetPermissionsResponseDTO(Set<String> permissions) {
        Permissions = permissions;
    }

    public Set<String> getPermissions() {
        return Permissions;
    }
}
