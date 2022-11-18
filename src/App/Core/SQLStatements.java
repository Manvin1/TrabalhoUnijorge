package App.Core;

public class SQLStatements {

    static public final String stmtPessoaTable = 
            "CREATE TABLE IF NOT EXISTS Pessoa" 
            + "(" 
                + "Login VARCHAR(20) NOT NULL PRIMARY KEY,"
                + "Senha VARCHAR(20) NOT NULL,"
                + "Email VARCHAR(30) NOT NULL,"
                + "Telefone VARCHAR(11) NOT NULL"
            + ");";
    static public final String stmtProjetoTable =
            "CREATE TABLE IF NOT EXISTS Projeto"
            +"("
                +"Id BIGINT PRIMARY KEY AUTO_INCREMENT,"
                +"PubLogin VARCHAR(20) NOT NULL,"
                +"Titulo VARCHAR(20) NOT NULL,"
                +"Resumo TEXT,"
                +"Descricao TEXT,"
                +"Cidade VARCHAR(20),"
                +"Estado VARCHAR(20),"
                + "FOREIGN KEY (PubLogin)"
                +    " REFERENCES Pessoa (Login)"
                +       " ON UPDATE NO ACTION"
                +       " ON DELETE NO ACTION"
            +")";

    static public final String stmtCategoriaTable =
            "CREATE TABLE IF NOT EXISTS Categoria"
            +"("
            +    "Id BIGINT PRIMARY KEY AUTO_INCREMENT,"
            +   " Nome VARCHAR(20) UNIQUE"
            +");";

    static public final String stmtPublicacaoTable =
            "CREATE TABLE IF NOT EXISTS Autores"
            +"("
            +    "IdProjeto BIGINT,"
            +    "Nome VARCHAR(20),"
            +    "PRIMARY KEY (IdProjeto, NOME),"
            +    "FOREIGN KEY (IdProjeto)"
            +        " REFERENCES Projeto (Id)"
            +            " ON UPDATE NO ACTION"
            +            " ON DELETE NO ACTION"
            +");";

    static public final String stmtProjCat =
        "CREATE TABLE IF NOT EXISTS ProjetoCategoria"
        +"("
        +    "IdProjeto BIGINT,"
        +    "IdCat BIGINT,"
        +    "PRIMARY KEY (IdProjeto, IdCat),"
        +    "FOREIGN KEY (IdProjeto)"
        +        " REFERENCES Projeto (Id)"
        +            " ON UPDATE NO ACTION"
        +            " ON DELETE NO ACTION,"
        +    "FOREIGN KEY (IdCat)"
        +        " REFERENCES Categoria (Id)"
        +            " ON UPDATE NO ACTION"
        +            " ON DELETE NO ACTION"
        +");";

    static public final String stmt_check = "SELECT * FROM Pessoa WHERE Login = ? AND Senha = ?;";
    static public final String stmt_registerUser = "INSERT INTO Pessoa (Login, Senha, Email, Telefone) VALUES (?, ?, ?, ?);";

    static public final String stmt_insertEvent = "INSERT INTO Projeto (Titulo, PubLogin, Resumo, Descricao, Cidade, Estado) VALUES (?, ?, ?, ?, ?, ?);";
    static public final String stmt_insertPubli = "INSERT INTO Autores (IdProjeto, Nome) VALUES (?, ?);";
    static public final String stmt_insertProjCateg = "INSERT INTO ProjetoCategoria (IdProjeto, IdCat) VALUES (?, ?);";
    static public final String stmt_insertCategory = "INSERT INTO Categoria (Nome) VALUES (?);";

    static public final String stmt_projectCount =  "SELECT COUNT(Id) FROM Projeto;";
    static public final String stmt_userCount =  "SELECT COUNT(Login) FROM Pessoa;";
    static public final String stmt_categories =  "SELECT * FROM Categoria;";

    static public final String stmt_allProjectShort =  "SELECT Id, Titulo, Resumo FROM Projeto;";
    static public final String stmt_allProject =  "SELECT * FROM Projeto;";
    static public final String stmt_nomesPerProject = "SELECT Nome FROM Autores WHERE IdProjeto = ?;";
    static public final String stmt_catPerProject = 
            "SELECT IdCat, Nome"
            +    " FROM ProjetoCategoria as PC"
            +    " INNER JOIN Categoria as C"
            +    " ON PC.IdProjeto = C.Id"
            +    " WHERE IdProjeto = ?;";

    static public final String stmt_getProject = "SELECT * FROM Projeto Where Id = ?";

}
