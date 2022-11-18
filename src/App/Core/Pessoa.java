package App.Core;

public class Pessoa {
    public String login = null;
    public String email = null;
    public String telefone = null;

    public Pessoa(String login, String email, String telefone)
    {
        this.login = login;
        this.email = email;
        this.telefone = telefone;
    }

    public Pessoa(PessoaDTO pessoa)
    {
        login = pessoa.login;
        email = pessoa.email;
        telefone = pessoa.telefone;
    }
}
