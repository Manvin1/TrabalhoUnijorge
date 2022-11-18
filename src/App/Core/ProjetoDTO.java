package App.Core;

import java.util.ArrayList;

public class ProjetoDTO {
    public String titulo;
    public String pubLogin;
    public String resumo;
    public String dption;
    public String cidade;
    public String estado;
    public ArrayList<Categoria> categoriaList;

    public ProjetoDTO(String titulo, String pubLogin, String resumo, String dption, String cidade, String estado, ArrayList<Categoria> categoriaList)
    {
        this.titulo = titulo;
        this.pubLogin = pubLogin;
        this.dption = dption;
        this.resumo = resumo;
        this.cidade = cidade;
        this.estado = estado;
        this.categoriaList = categoriaList;
    }

    public void addCategoria(Categoria categoria)
    {
        categoriaList.add(categoria);
    }

}
