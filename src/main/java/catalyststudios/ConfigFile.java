package catalyststudios;

public class ConfigFile {
    public ConfigFile(String token, long[] allowedOptRoles, long verifyRole) {
        this.token = token;
        this.allowedOptRoles = allowedOptRoles;
        this.verifyRole = verifyRole;
    }

    String token;
    long[] allowedOptRoles;
    long verifyRole;

    public String getToken() {
        return token;
    }

    public long[] getAllowedOptRoles() {
        return allowedOptRoles;
    }

    public void setAllowedOptRoles(long[] allowedOptRoles) {
        this.allowedOptRoles = allowedOptRoles;
    }

    public long getVerifyRole() {
        return verifyRole;
    }

    public void setVerifyRole(long verifyRole) {
        this.verifyRole = verifyRole;
    }
}
