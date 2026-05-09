import model.Categoria;
import model.Filme;
import system.Cliente;
import system.Servidor;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Servidor servidor = new Servidor();
        Scanner scanner = new Scanner(System.in);

        // Popular 1000 filmes
        String[] nomes = {"Matrix", "Interestelar", "O Poderoso Chefão", "Pulp Fiction",
                "O Senhor dos Anéis", "Batman", "Superman", "Vingadores", "Homem Aranha",
                "Jurassic Park"};
        String[] sinopses = {"Um hacker descobre a verdade sobre a realidade",
                "Astronautas viajam pelo universo", "A história de uma família mafiosa",
                "Histórias entrelaçadas de crime", "Uma jornada para destruir um anel",
                "Um herói protege Gotham", "O homem de aço salva o mundo",
                "Heróis se unem para salvar o universo", "Um jovem descobre seus poderes",
                "Dinossauros voltam à vida"};
        Categoria[] categorias = Categoria.values();

        for (int i = 1; i <= 1000; i++) {
            String nome = nomes[i % nomes.length] + " " + i;
            String sinopse = sinopses[i % sinopses.length];
            int ano = 1990 + (i % 35);
            Categoria categoria = categorias[i % categorias.length];
            servidor.inserirFilme(new Filme(i, nome, sinopse, ano, categoria));
        }

        Cliente cliente = new Cliente(servidor);

        // Popular cache com 50 filmes
        for (int i = 1; i <= 50; i++) {
            cliente.buscar(i);
        }

        // Interface do cliente
        boolean rodando = true;
        while (rodando) {
            System.out.println("\n=== STREAMING APP ===");
            System.out.println("Escolha uma categoria:");
            for (int i = 0; i < categorias.length; i++) {
                System.out.println("[" + (i + 1) + "] " + categorias[i]);
            }
            System.out.println("[0] Buscar por ID");
            System.out.println("[-1] Executar simulação das 20 consultas");
            System.out.println("[-2] Sair");
            System.out.print("Opção: ");

            int opcao = scanner.nextInt();

            if (opcao == -2) {
                rodando = false;
            } else if (opcao == -1) {
                executarSimulacao(servidor, cliente, categorias);
            } else if (opcao == 0) {
                System.out.print("Digite o ID do filme: ");
                int id = scanner.nextInt();
                Filme f = cliente.buscar(id);
                if (f != null) {
                    System.out.println("\nFilme encontrado!");
                    System.out.println("Nome: " + f.getNome());
                    System.out.println("Ano: " + f.getAno());
                    System.out.println("Sinopse: " + f.getSinopse());
                    System.out.println("Categoria: " + f.getCategoria());
                } else {
                    System.out.println("Filme não encontrado!");
                }
            } else if (opcao >= 1 && opcao <= categorias.length) {
                Categoria categoriaSelecionada = categorias[opcao - 1];
                System.out.println("\n=== Filmes de " + categoriaSelecionada + " ===");
                for (int i = 1; i <= 1000; i++) {
                    Categoria cat = categorias[i % categorias.length];
                    if (cat == categoriaSelecionada) {
                        String nome = nomes[i % nomes.length] + " " + i;
                        System.out.println("[ID: " + i + "] " + nome);
                    }
                }
                System.out.print("\nDigite o ID para assistir: ");
                int id = scanner.nextInt();
                Filme f = cliente.buscar(id);
                if (f != null) {
                    System.out.println("\nAssistindo: " + f.getNome());
                    System.out.println("Ano: " + f.getAno());
                    System.out.println("Sinopse: " + f.getSinopse());
                } else {
                    System.out.println("Filme não encontrado!");
                }
            }
        }
        scanner.close();
    }

    static void executarSimulacao(Servidor servidor, Cliente cliente, Categoria[] categorias) {
        System.out.println("\n=== SIMULAÇÃO DE 20 CONSULTAS ===\n");

        System.out.println("--- Consultas inválidas ---");
        cliente.buscar(9999);
        cliente.buscar(8888);

        System.out.println("\n--- Consultas com cache (AVL) ---");
        int[] idsCache = {1, 5, 10, 20, 35, 50};
        for (int id : idsCache) {
            Filme f = cliente.buscar(id);
            if (f != null) System.out.println("Filme encontrado: " + f.getNome());
        }

        System.out.println("\n--- Consultas sem indexação (busca linear) ---");
        int[] idsSemHash = {51, 200, 400, 600, 800, 950};
        for (int id : idsSemHash) {
            Filme f = servidor.buscarSemHash(id);
            if (f != null) System.out.println("Filme encontrado: " + f.getNome());
        }

        System.out.println("\n--- Consultas com indexação (hash) ---");
        int[] idsComHash = {52, 201, 401, 601, 801, 951};
        for (int id : idsComHash) {
            Filme f = servidor.buscarComHash(id);
            if (f != null) System.out.println("Filme encontrado: " + f.getNome());
        }

        System.out.println("\n=== ANÁLISE ===");
        System.out.println("Cache (AVL): busca em O(log n) — poucas comparações");
        System.out.println("Sem indexação: busca linear O(n) — muitas comparações");
        System.out.println("Com indexação: busca via hash O(1) — mínimo de comparações");
    }
}