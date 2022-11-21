package App.Core;

/**
 *  < Inspirido no Authenticate do Spring.
 */
public class Authenticate {
    public String login;
    public String pwd;

    public Authenticate(String login, String pwd)
    {
        this.login = login;
        this.pwd = pwd;
    }
}
