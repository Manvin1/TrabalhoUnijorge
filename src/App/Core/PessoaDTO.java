package App.Core;

public class PessoaDTO {
    public String login = null;
    public String pwd = null;
    public String email = null;
    public String telefone = null;

    public PessoaDTO(String login, String pwd, String email, String telefone)
    {
        this.login = login;
        this.pwd = pwd;
        this.email = email;
        this.telefone = telefone;
    }

}
