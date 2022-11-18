package App.Managers;

import App.Core.*;

import java.sql.*;
import java.util.Optional;
import java.util.ArrayList;

public class DataBaseManager {

    public static DataBaseManager get() {
        return dbManager;
    }

    /**
     * < Checa se um usuário está cadastrado e retorna-o, se estiver.
     * 
     * @param authenticate
     * @return Optional<PessoaDto>
     */
    public Optional<Pessoa> checkUser(Authenticate authenticate) {
        Optional<Connection>  ctionOpt  = ConnectionManager.getConnection();
        if (ctionOpt.isPresent()) {
            try (Connection connection = ctionOpt.get()) {
                
                PreparedStatement stmt = connection.prepareStatement(SQLStatements.stmt_check);
                stmt.setString(1, authenticate.login);
                stmt.setString(2, authenticate.pwd);

                ResultSet result = stmt.executeQuery();
                if (result.next()) {
                    String login = result.getString("Login");
                    String email = result.getString("Email");
                    String telefone = result.getString("Telefone");

                    stmt.close();
                    return Optional.of(new Pessoa(login, email, telefone));
                } 
                else {
                    stmt.close();
                    return Optional.empty();
                }
                
            } catch (SQLException e) {
                System.out.println("Ocorreu um erro na query da database.");
                e.printStackTrace();
            }
        } else {
            System.out.println("DataBaseManager: Não foi possível estabeler conexão para query na database.");
        }
        return Optional.empty();
    }

    /**
     * < Registra um usuário e retorna os seus dados, caso seja bem sucedido. Senão,
     * retorna-se um optional não engajado.
     * 
     * @param login
     * @param pwd
     * @param email
     * @param telefone
     * @return Optional
     */
    public Optional<Pessoa> registerUser(PessoaDTO pessoa) {
        Optional<Connection>  ctionOpt  = ConnectionManager.getConnection();
        if (ctionOpt.isPresent()) {
            try (Connection connection = ctionOpt.get()) {
                PreparedStatement stmt = connection.prepareStatement(SQLStatements.stmt_registerUser);
                stmt.setString(1, pessoa.login);
                stmt.setString(2, pessoa.pwd);
                stmt.setString(3, pessoa.email);
                stmt.setString(4, pessoa.telefone);

                int result = stmt.executeUpdate();
                if (result != 0) {
                    stmt.close();
                    return Optional.of(new Pessoa(pessoa));
                } else {
                    stmt.close();
                    return Optional.empty();
                }
            } catch (SQLException e) {
                System.out.println("Ocorreu um erro na inserção do novo registro.");
                e.printStackTrace();
            }
        }
        return Optional.empty();
    }

    private DataBaseManager() {
        init();
    }

    /**
     * < Insere um projeto no banco de dados.
     * 
     * @param projetoDto
     * @param pessoaList
     * @return Um optional engajado, caso a inserção seja bem-sucedida, senão um
     *         emtpy optional.
     */
    public Optional<Projeto> insertProject(ProjetoDTO projetoDto, ArrayList<String> nomeList) {
        Optional<Connection>  ctionOpt  = ConnectionManager.getConnection();
        if (ctionOpt.isPresent()) {
            try (Connection connection = ctionOpt.get()) {
                connection.setAutoCommit(false);

                Optional<Integer> idOpt = insertProject(connection, projetoDto);

                if (idOpt.isPresent()) // project inserted
                {
                    Projeto projeto = new Projeto(projetoDto, nomeList);
                    projeto.id = idOpt.get();

                    if (insertPublishers(connection, projeto)) // publish inserted
                    {
                        if (insertProjectCategories(connection, projeto)) // categories inserted
                        {
                            connection.commit();
                            return Optional.of(projeto);
                        }
                    }
                }
                connection.rollback();
            } catch (SQLException e) // possivelmente leva à bugs se lançar exception, pois não é possível realizar o
                                     // rollback?
            {
                System.out.println("Ocorreu um erro no registro de projetos.");
                e.printStackTrace();
            }
        }
        return Optional.empty();
    }

    /**
     * < Retorna a quantidade de projetos cadastrado. Caso ocorra algum erro, é
     * retornado -1.
     * 
     * @return Quantidade de projetos ou -1;
     */
    public int getProjectCount() {
        Optional<Connection>  ctionOpt  = ConnectionManager.getConnection();
        if (ctionOpt.isPresent()) {
            try (Connection connection = ctionOpt.get()) {
                PreparedStatement stmt = connection.prepareStatement(SQLStatements.stmt_projectCount);

                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    return rs.getInt(1);
                }
            } catch (SQLException e) // possivelmente leva à bugs se lançar exception, pois não é possível realizar o
                                     // rollback?
            {
                System.out.println("Ocorreu um erro no registro de projetos.");
                e.printStackTrace();
            }
        }
        return -1;
    }

    /**
     * < Retorna a quantidade de usuarios cadastrado. Caso ocorra algum erro, é
     * retornado -1.
     * 
     * @return Quantidade de usuarios ou -1;
     */
    public int getUserCount() {
        Optional<Connection>  ctionOpt  = ConnectionManager.getConnection();
        if (ctionOpt.isPresent()) {
            try (Connection connection = ctionOpt.get()) {
                PreparedStatement stmt = connection.prepareStatement(SQLStatements.stmt_userCount);

                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    return rs.getInt(1);
                }

            } catch (SQLException e) // possivelmente leva à bugs se lançar exception, pois não é possível realizar o
                                     // rollback?
            {
                System.out.println("Ocorreu um erro no registro de projetos.");
                e.printStackTrace();
            }
        }

        return -1;
    }

    /**
     * < Retorna a quantidade de usuarios cadastrado. Caso ocorra algum erro, é
     * retornado -1.
     * 
     * @return Quantidade de usuarios ou -1;
     */
    public ArrayList<Categoria> getCategorias() {
        Optional<Connection>  ctionOpt  = ConnectionManager.getConnection();
        ArrayList<Categoria> catList = new ArrayList<Categoria>();
        if (ctionOpt.isPresent()) {
            try (Connection connection = ctionOpt.get()) {
                PreparedStatement stmt = connection.prepareStatement(SQLStatements.stmt_categories);

                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    final int id = rs.getInt("Id");
                    final String nome = rs.getString("Nome");

                    Categoria categoria = new Categoria(id, nome);
                    catList.add(categoria);
                }
            } catch (SQLException e) // possivelmente leva à bugs se lançar exception, pois não é possível realizar o
                                     // rollback?
            {
                System.out.println("Ocorreu um erro no registro de projetos.");
                e.printStackTrace();
            }
        }
        return catList;
    }

    public ArrayList<Projeto> getAllProjectsShort() {
        Optional<Connection>  ctionOpt  = ConnectionManager.getConnection();
        ArrayList<Projeto> projetoList = new ArrayList<Projeto>();

        if (ctionOpt.isPresent()) 
        {
            try (Connection connection = ctionOpt.get()) 
            {
                PreparedStatement stmt = connection.prepareStatement(SQLStatements.stmt_allProjectShort);
                PreparedStatement stmt_cat = connection.prepareStatement(SQLStatements.stmt_catPerProject);
                PreparedStatement stmt_nom = connection.prepareStatement(SQLStatements.stmt_nomesPerProject);

                ResultSet rs = stmt.executeQuery();
                
                while (rs.next()) //para cada tupla
                {
                    int id = rs.getInt("Id");
                    String titulo = rs.getString("Titulo");
                    String resumo = rs.getString("Resumo");
                    ArrayList<String> nomeList = new ArrayList<String>();
                    ArrayList<Categoria> catList = new ArrayList<Categoria>();

                    stmt_nom.setInt(1, id);
                    ResultSet rs_nom = stmt_nom.executeQuery();
                    while (rs_nom.next()) //nome dos autores
                    {
                        nomeList.add(rs_nom.getString("Nome"));
                    }

                    stmt_cat.setInt(1, id);
                    ResultSet rs_cat = stmt_cat.executeQuery();
                    while (rs_cat.next()) //categorias
                    {
                        catList.add(new Categoria(rs_cat.getInt("IdCat"), rs_cat.getString("Nome")));
                    }

                    Projeto projeto = new Projeto(id, "", titulo, resumo, "", "", "", nomeList, catList);

                    projetoList.add(projeto);
                }
            } 
            catch (SQLException exp) 
            {
                //nothing
            }
        }
        return projetoList;
    }

    public ArrayList<Projeto> getAllProjects() {
        Optional<Connection>  ctionOpt  = ConnectionManager.getConnection();
        ArrayList<Projeto> projetoList = new ArrayList<Projeto>();

        if (ctionOpt.isPresent()) 
        {
            try (Connection connection = ctionOpt.get()) 
            {
                PreparedStatement stmt = connection.prepareStatement(SQLStatements.stmt_allProject);
                PreparedStatement stmt_cat = connection.prepareStatement(SQLStatements.stmt_catPerProject);
                PreparedStatement stmt_nom = connection.prepareStatement(SQLStatements.stmt_nomesPerProject);

                ResultSet rs = stmt.executeQuery();
                
                while (rs.next()) //para cada tupla
                {
                    int id = rs.getInt("Id");
                    String pubLogin = rs.getString("PubLogin"); //ideal seria outra query
                    String titulo = rs.getString("Titulo");
                    String resumo = rs.getString("Resumo");
                    String descricao = rs.getString("Descricao");
                    String cidade = rs.getString("Cidade");
                    String estado = rs.getString("Estado");

                    ArrayList<String> nomeList = new ArrayList<String>();
                    ArrayList<Categoria> catList = new ArrayList<Categoria>();

                    stmt_nom.setInt(1, id);
                    ResultSet rs_nom = stmt_nom.executeQuery();
                    while (rs_nom.next()) //nome dos autores
                    {
                        nomeList.add(rs_nom.getString("Nome"));
                    }

                    stmt_cat.setInt(1, id);
                    ResultSet rs_cat = stmt_cat.executeQuery();
                    while (rs_cat.next()) //categorias
                    {
                        catList.add(new Categoria(rs_cat.getInt("IdCat"), rs_cat.getString("Nome")));
                    }

                    Projeto projeto = new Projeto(id, pubLogin, titulo, resumo, descricao, cidade, estado, nomeList, catList);

                    projetoList.add(projeto);
                }
            } 
            catch (SQLException exp) 
            {
                System.out.println(exp.getMessage());
            }
        }
        return projetoList;
    }

    public Optional<Projeto> getProject(int id)
    {
        Optional<Connection>  ctionOpt  = ConnectionManager.getConnection();
        if (ctionOpt.isPresent()) 
        {
            try (Connection connection = ctionOpt.get()) 
            {
                PreparedStatement stmt = connection.prepareStatement(SQLStatements.stmt_getProject);
                PreparedStatement stmt_cat = connection.prepareStatement(SQLStatements.stmt_catPerProject);
                PreparedStatement stmt_nom = connection.prepareStatement(SQLStatements.stmt_nomesPerProject);

                stmt.setInt(1, id);

                ResultSet rs = stmt.executeQuery();
                
                if (rs.next()) //para cada tupla
                {
                    String pubLogin = rs.getString("PubLogin"); //ideal seria outra query
                    String titulo = rs.getString("Titulo");
                    String resumo = rs.getString("Resumo");
                    String descricao = rs.getString("Descricao");
                    String cidade = rs.getString("Cidade");
                    String estado = rs.getString("Estado");

                    ArrayList<String> nomeList = new ArrayList<String>();
                    ArrayList<Categoria> catList = new ArrayList<Categoria>();

                    stmt_nom.setInt(1, id);
                    ResultSet rs_nom = stmt_nom.executeQuery();
                    while (rs_nom.next()) //nome dos autores
                    {
                        nomeList.add(rs_nom.getString("Nome"));
                    }

                    stmt_cat.setInt(1, id);
                    ResultSet rs_cat = stmt_cat.executeQuery();
                    while (rs_cat.next()) //categorias
                    {
                        catList.add(new Categoria(rs_cat.getInt("IdCat"), rs_cat.getString("Nome")));
                    }

                    Projeto projeto = new Projeto(id, pubLogin, titulo, resumo, descricao, cidade, estado, nomeList, catList);

                    return Optional.of(projeto);
                }
            } 
            catch (SQLException exp) 
            {
                System.out.println(exp.getMessage());
            }
        }
        return Optional.empty();       
    }

    private void init() {
        Optional<Connection>  ctionOpt  = ConnectionManager.getConnection();
        if (ctionOpt.isPresent()) {
            try (Connection connection = ctionOpt.get()) {
                Statement stmt = connection.createStatement();

                stmt.executeUpdate(SQLStatements.stmtPessoaTable);
                stmt.executeUpdate(SQLStatements.stmtProjetoTable);
                stmt.executeUpdate(SQLStatements.stmtCategoriaTable);
                stmt.executeUpdate(SQLStatements.stmtPublicacaoTable);
                stmt.executeUpdate(SQLStatements.stmtProjCat);

                PreparedStatement pStmt = connection.prepareStatement(SQLStatements.stmt_insertCategory);

                pStmt.setString(1, "Tecnologia");
                pStmt.executeUpdate();

                pStmt.setString(1, "Matemática");
                pStmt.executeUpdate();

                pStmt.setString(1, "Física");
                pStmt.executeUpdate();

                pStmt.setString(1, "Química");
                pStmt.executeUpdate();

                System.out.println("Database Initialized com sucesso.");
                
                stmt.close();
                pStmt.close();
            } catch (SQLException e) {
                // Quando o programa é iniciado duas vezes ocorre dupla inserção das categorias.
                System.out.println("Ocorreu um erro na inicialização da database.");
                e.printStackTrace();
            }
        }
    }

    /**
     * < Insere Publicadores de um projeto em uma conexão pré-existente.
     * 
     * @param connection
     * @param projeto
     * @param pessoaList
     * @return true se todas as inserções forem bem-sucedidas (se alguma), senão
     *         false.
     */
    private boolean insertPublishers(Connection connection, Projeto projeto) {
        try {
            PreparedStatement stmt = connection.prepareStatement(SQLStatements.stmt_insertPubli);

            for (String nome : projeto.nomeList) // authors inserted
            {
                stmt.setInt(1, projeto.id);
                stmt.setString(2, nome);

                int result = stmt.executeUpdate();
                if (result == 0) {
                    System.out.println("insertPublishers: It was not possibile to insert an author of an project");
                    stmt.close();
                    return false;
                }
            }
            stmt.close();
            return true;
        } catch (SQLException exp) {
            // nothing
        }
        return false;
    }

    /**
     * < Insere Categorias de um projeto em uma conexão pré-existente.
     * 
     * @param connection
     * @param projeto
     * @return true se todas as inserções forem bem-sucedidas (se alguma), senão
     *         false.
     */
    private boolean insertProjectCategories(Connection connection, Projeto projeto) {
        try {
            PreparedStatement stmt = connection.prepareStatement(SQLStatements.stmt_insertProjCateg);

            stmt.setInt(1, projeto.id);
            for (Categoria categoria : projeto.categoriaList) 
            {
                stmt.setInt(2, categoria.id);
                int result = stmt.executeUpdate();

                if (result == 0) {
                    System.out.println("inserEventCategories: Não foi possível inserir alguma categoria");
                    stmt.close();
                    return false;
                }
            }
            stmt.close();
            return true;
        } catch (SQLException exp) {
            System.out.println(exp.getMessage());
        }
        return false;
    }

    /**
     * < Insere um projeto em uma conexão pré-existente.
     * 
     * @param connection
     * @param projetoDto
     * @return Optional engajado com o id gerado, caso seja bem-sucedido, senão um
     *         empty optional.
     */
    private Optional<Integer> insertProject(Connection connection, ProjetoDTO projetoDto) {
        try {
            PreparedStatement stmt = connection.prepareStatement(SQLStatements.stmt_insertEvent,
                    Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, projetoDto.titulo);
            stmt.setString(2, projetoDto.pubLogin);
            stmt.setString(3, projetoDto.resumo);
            stmt.setString(4, projetoDto.dption);
            stmt.setString(5, projetoDto.cidade);
            stmt.setString(6, projetoDto.estado);

            int result = stmt.executeUpdate();

            if (result != 0) {
                ResultSet resultSet = stmt.getGeneratedKeys();
                if (resultSet.next()) {
                    stmt.close();
                    return Optional.of(resultSet.getInt(1));
                } else {
                    stmt.close();
                    System.out.println("insertProject: Não foi gerada uma Key para um evento");
                }
            }
        } catch (SQLException exp) {
            System.out.println(exp.getMessage());
        }

        return Optional.empty();
    }

    private static DataBaseManager dbManager = new DataBaseManager();
}
