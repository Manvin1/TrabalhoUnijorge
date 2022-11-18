package App.Core;

public class Categoria {
    public int id;
    public String nome;

    public Categoria (int id, String nome)
    {
        this.id = id;
        this.nome = nome;
    }

    @Override
    public String toString()
    {
        return '[' + nome + ']';
    }
}
