package org.oakbricks.borealis.core.config;

import java.util.Collection;
import java.util.Collections;

public class ConfigFile {
    public ConfigFile(String token, boolean optBlacklistMode, Collection<String> optRoles, String verifyRole) {
        this.token = token;
        this.optBlacklistMode = optBlacklistMode;
        this.optRoles = optRoles;
        this.verifyRole = verifyRole;
    }

    public ConfigFile(String token, boolean optBlacklistMode, Collection<String> optRoles) {
        this.token = token;
        this.optBlacklistMode = optBlacklistMode;
        this.optRoles = optRoles;
    }

    public ConfigFile(String token, String verifyRole) {
        this.token = token;
        this.optBlacklistMode= false;
        this.optRoles = Collections.emptyList();
        this.verifyRole = verifyRole;
    }

    public ConfigFile(String token) {
        this.token = token;
        this.optRoles = Collections.emptyList();
        this.verifyRole = "";
    }

    private String token;
    private boolean optBlacklistMode;
    private Collection<String> optRoles;
    private String verifyRole;

    public String getToken() {
        return token;
    }

    public Collection<String> getOptRoles() {
        return optRoles;
    }

    public void setOptRoles(Collection<String> optRoles) {
        this.optRoles = optRoles;
    }

    public String getVerifyRole() {
        return verifyRole;
    }

    public void setVerifyRole(String verifyRole) {
        this.verifyRole = verifyRole;
    }
}
