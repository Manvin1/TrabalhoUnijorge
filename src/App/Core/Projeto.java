package App.Core;

import java.util.ArrayList;

public class Projeto {
    public int id;
    public String pubLogin;
    public String titulo;
    public String resumo;
    public String dption;
    public String cidade;
    public String estado;
    public ArrayList<String> nomeList;
    public ArrayList<Categoria> categoriaList;

    public Projeto(int id, String pubLogin, String titulo, String resumo, String dption, String cidade, String estado, ArrayList<String> nomeList, ArrayList<Categoria> categoriaList)
    {
        this.id = id;
        this.pubLogin = pubLogin;
        this.titulo = titulo;
        this.dption = dption;
        this.resumo = resumo;
        this.cidade = cidade;
        this.estado = estado;
        this.nomeList = nomeList;
        this.categoriaList = categoriaList;
    }

    public Projeto(ProjetoDTO projeto, ArrayList<String> nomeList)
    {
        this.titulo = projeto.titulo;
        this.pubLogin = projeto.pubLogin;
        this.dption = projeto.dption;
        this.cidade = projeto.cidade;
        this.estado = projeto.estado;
        this.resumo = projeto.resumo;
        this.categoriaList = projeto.categoriaList;
        this.nomeList = nomeList;
    }

    public void addPessoa(String pessoa)
    {
        nomeList.add(pessoa);
    }

}
