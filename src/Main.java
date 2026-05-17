import model.Categoria;
import model.Filme;
import structure.NoLista;
import system.Cliente;
import system.Servidor;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Servidor servidor = new Servidor();
        Scanner scanner = new Scanner(System.in);
        Categoria[] categorias = Categoria.values();

        String[] bases = {
                "O Poderoso Chefão", "Star Wars", "Matrix", "Vingadores", "O Senhor dos Anéis",
                "Harry Potter", "Jurassic Park", "Batman", "Interestelar", "Pulp Fiction",
                "Gladiador", "O Rei Leão", "Avatar", "Toy Story", "Coringa", "Duna", "Inception"
        };

        for (int i = 1; i <= 1000; i++) {
            Categoria cat = categorias[i % categorias.length];
            String nomeBase = bases[i % bases.length];

            int versao = (i / bases.length) + 1;
            String nomeReal = (versao == 1) ? nomeBase : nomeBase + " " + versao;

            String sinopse = "Um clássico da categoria " + cat;
            int ano = 1970 + (i % 56);

            servidor.inserirFilme(new Filme(i, nomeReal, sinopse, ano, cat));
        }

        Cliente cliente = new Cliente(servidor);

        System.out.println("Aquecendo cache inicial...");
        for (int i = 1; i <= 50; i++) {
            cliente.buscar(i);
        }
        System.out.println("Login: ");
        System.out.println("Usuario: ");
        String usuario = scanner.nextLine();
        System.out.println("Senha: ");
        String senha = scanner.nextLine();
        boolean rodando = true;
        while (rodando) {
            System.out.println("\n=== STREAMING APP - PRÁTICA ED II ===");
            System.out.println("Escolha uma categoria:");
            for (int i = 0; i < categorias.length; i++) {
                System.out.println("[" + (i + 1) + "] " + categorias[i]);
            }
            System.out.println("[8] Buscar diretamente por ID");
            System.out.println("[-1] Executar Simulação de Desempenho (20 consultas)");
            System.out.println("[-2] Sair");
            System.out.print("Opção: ");

            int opcao = scanner.nextInt();
            scanner.nextLine();

            if (opcao == -2) {
                rodando = false;
            } else if (opcao == -1) {
                executarSimulacao(servidor, cliente);
            } else if (opcao == 8) {
                System.out.print("Digite o ID: ");
                int id = scanner.nextInt();
                scanner.nextLine();
                exibirFilme(cliente.buscar(id));
            } else if (opcao >= 1 && opcao <= categorias.length) {
                Categoria sel = categorias[opcao - 1];
                System.out.println("\n--- Catálogo de " + sel + " ---");


                NoLista temp = servidor.getListaPorCategoria(sel).getCabeca();
                while (temp != null) {
                    System.out.println("- " + temp.getFilme().getNome());
                    temp = temp.getProximo();
                }

                System.out.print("\nDigite o NOME do filme: ");
                String nomeBusca = scanner.nextLine();

                Filme achado = servidor.buscarPorNomeLinear(nomeBusca);

                if (achado != null && achado.getCategoria() == sel) {
                    exibirFilme(cliente.buscar(achado.getId()));
                } else {
                    System.out.println("Filme não encontrado nesta categoria.");
                }
            }
        }
        scanner.close();
    }

    private static void exibirFilme(Filme f) {
        if (f != null) {
            System.out.println("\n***********************************");
            System.out.println("REPRODUZINDO: " + f.getNome());
            System.out.println("Ano: " + f.getAno() + " | " + f.getCategoria());
            System.out.println("***********************************\n");
        }
    }

    public static void executarSimulacao(Servidor servidor, Cliente cliente) {
        System.out.println("\n======================================================");
        System.out.println("   RELATÓRIO DE DESEMPENHO (20 CONSULTAS ESTRATÉGICAS) ");
        System.out.println("======================================================\n");

        System.out.println("--- [AVL] Testando filmes no Cache ---");
        for (int i = 1; i <= 5; i++) {
            cliente.buscar(i);
        }

        System.out.println("\n--- [HASH] Testando busca indexada no servidor ---");
        int[] ids = {150, 250, 350, 450, 550, 650, 750, 850, 950, 999};
        for (int id : ids) {
            cliente.buscar(id);
        }

        System.out.println("\n--- [LINEAR] Comparação sem indexação ---");
        servidor.buscarSemHash(500);
        servidor.buscarSemHash(990);


        System.out.println("\n--- [ERRO] Buscando dados inexistentes ---");
        cliente.buscar(5000);
        System.out.println("Filme não encontrado no catálogo");

        System.out.println("\n======================================================");
    }
}