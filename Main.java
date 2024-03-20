import java.text.DecimalFormat;
import java.util.Scanner;

class ContaBancaria {
    private String nome;
    private int idade;
    private int numeroConta;
    private String senha;
    private double saldo;

    // Construtor
    public ContaBancaria(String nome, int idade, int numeroConta, String senha) {
        this.nome = nome;
        this.idade = idade;
        this.numeroConta = numeroConta;
        this.senha = senha;
        this.saldo = 0.0;
    }

    // Métodos para acesso aos atributos
    public String getNome() {
        return nome;
    }

    public int getIdade() {
        return idade;
    }

    public int getNumeroConta() {
        return numeroConta;
    }

    public String getSenha() {
        return senha;
    }

    public double getSaldo() {
        return saldo;
    }

    // Métodos para operações bancárias
    public void depositar(double valor) {
        saldo += valor;
        System.out.println("Deposito de R$" + valor + " realizado com sucesso.");
    }

    public void sacar(double valor) {
        if (valor <= saldo) {
            saldo -= valor;
            System.out.println("Saque de R$" + valor + " realizado com sucesso.");
        } else {
            System.out.println("Saldo insuficiente.");
        }
    }

    public void consultarSaldo() {
        System.out.println("Saldo atual: R$" + saldo);
    }

    // Método para investimentos
    public void investir(String tipoInvestimento, double valorInvestimento) {
        switch (tipoInvestimento) {
            case "poupanca":
                System.out.println("Investindo na poupanca...");
                break;
            case "cdi":
                System.out.println("Investindo no CDI...");
                break;
            case "acoes":
                System.out.println("Investindo em acoes...");
                break;
            case "fundos imobiliarios":
                System.out.println("Investindo em fundos imobiliarios...");
                break;
            default:
                System.out.println("Opcao de investimento invalida.");
        }

        saldo -= valorInvestimento;
        System.out.println("Valor de R$" + valorInvestimento + " investido com sucesso");
    }

    public void consultarInvestimentos(Scanner scanner) {
        DecimalFormat df = new DecimalFormat("#.##");

        System.out.println("\n--- Consultar Investimentos ---");
        System.out.println("Escolha o tipo de investimento:");
        System.out.println("1. Poupanca");
        System.out.println("2. CDI");
        System.out.println("3. Acoes");
        System.out.println("4. Fundos Imobiliarios");
        System.out.print("Escolha uma opcao: ");
        int opcaoInvestimento = scanner.nextInt();
        double taxaAnual = 0.0;

        switch (opcaoInvestimento) {
            case 1:
                System.out.println("Poupanca:");
                taxaAnual = 0.06;
                break;
            case 2:
                System.out.println("CDI:");
                taxaAnual = 0.11;
                break;
            case 3:
                System.out.println("Acoes:");
                taxaAnual = 0.15;
                break;
            case 4:
                System.out.println("Fundos Imobiliarios:");
                taxaAnual = 0.12;
                break;
            default:
                System.out.println("Opcao invalida.");
                return;
        }

        System.out.print("Digite o periodo (em anos) para a projecao de rentabilidade: ");
        int periodo = scanner.nextInt();

        System.out.print("Digite o valor para a projecao de rentabilidade: ");
        double valorProjecao = scanner.nextDouble();

        double investimentoProjetado = calcularProjecaoInvestimentoJurosCompostos(valorProjecao, taxaAnual, periodo);
        System.out.println("Rentabilidade projetada para " + periodo + " anos: R$" + df.format(investimentoProjetado));
    }

    private double calcularProjecaoInvestimentoJurosCompostos(double valorProjecao, double taxaAnual, int periodo) {
        double investimento = valorProjecao;
        investimento *= Math.pow(1 + taxaAnual, periodo);
        return investimento;
    }
}

public class Main {
    private static final int MAX_CONTAS = 100;
    private static ContaBancaria[] contas = new ContaBancaria[MAX_CONTAS];
    private static int numContas = 0;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int opcao;
        do {
            System.out.println("\n--- Menu Inicial ---");
            System.out.println("1. Criar Conta");
            System.out.println("2. Acessar Conta");
            System.out.println("3. Sair");
            System.out.print("Escolha uma opcao: ");
            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    criarConta(scanner);
                    break;
                case 2:
                    acessarConta(scanner);
                    break;
                case 3:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opcao invalida.");
            }
        } while (opcao != 3);

        scanner.close();
    }

    private static void criarConta(Scanner scanner) {
        System.out.println("\n--- Criar Conta ---");
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("Idade: ");
        int idade = scanner.nextInt();
        System.out.print("Numero da Conta: ");
        int numeroConta = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Senha: ");
        String senha = scanner.nextLine();

        // Verifica se o número da conta já existe
        for (int i = 0; i < numContas; i++) {
            if (contas[i].getNumeroConta() == numeroConta) {
                System.out.println("Ja existe uma conta com esse numero.");
                return;
            }
        }

        // Cria a nova conta
        contas[numContas++] = new ContaBancaria(nome, idade, numeroConta, senha);
        System.out.println("Conta criada com sucesso.");
    }

    private static void acessarConta(Scanner scanner) {
        System.out.println("\n--- Acessar Conta ---");
        System.out.print("Numero da Conta: ");
        int numeroConta = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Senha: ");
        String senha = scanner.nextLine();

        // Procura pela conta com o número e senha fornecidos
        for (int i = 0; i < numContas; i++) {
            if (contas[i].getNumeroConta() == numeroConta && contas[i].getSenha().equals(senha)) {
                menuOperacoes(scanner, contas[i]);
                return;
            }
        }

        System.out.println("Conta nao encontrada ou senha incorreta.");
    }

    private static void menuOperacoes(Scanner scanner, ContaBancaria conta) {
        int opcao;
        do {
            System.out.println("\n--- Menu Operacoes ---");
            System.out.println("1. Depositar");
            System.out.println("2. Sacar");
            System.out.println("3. Consultar Saldo");
            System.out.println("4. Consultar Investimentos");
            System.out.println("5. Investir");
            System.out.println("6. Voltar");
            System.out.print("Escolha uma opcao: ");
            opcao = scanner.nextInt();
    
            switch (opcao) {
                case 1:
                    System.out.print("Digite o valor a depositar: ");
                    double valorDeposito = scanner.nextDouble();
                    conta.depositar(valorDeposito);
                    break;
                case 2:
                    System.out.print("Digite o valor a sacar: ");
                    double valorSaque = scanner.nextDouble();
                    conta.sacar(valorSaque);
                    break;
                case 3:
                    conta.consultarSaldo();
                    break;
                case 4:
                    conta.consultarInvestimentos(scanner);
                    break;
                case 5:
                    if (conta.getSaldo() <= 0) {
                        System.out.println("Saldo insuficiente para investir.");
                    } else {
                        System.out.print("Digite o valor que deseja investir: ");
                        double valorInvestimento = scanner.nextDouble();
                        if (valorInvestimento > 0 && valorInvestimento <= conta.getSaldo()) {
                            System.out.println("Escolha o tipo de investimento:");
                            System.out.println("1. Poupanca");
                            System.out.println("2. CDI");
                            System.out.println("3. Acoes");
                            System.out.println("4. Fundos Imobiliarios");
                            System.out.print("Escolha uma opcao: ");
                            int tipoInvestimento = scanner.nextInt();
                            switch (tipoInvestimento) {
                                case 1:
                                    conta.investir("poupanca", valorInvestimento);
                                    break;
                                case 2:
                                    conta.investir("cdi", valorInvestimento);
                                    break;
                                case 3:
                                    conta.investir("acoes", valorInvestimento);
                                    break;
                                case 4:
                                    conta.investir("fundos imobiliarios", valorInvestimento);
                                    break;
                                default:
                                    System.out.println("Opcao invalida.");
                            }
                        } else {
                            System.out.println("Valor de investimento invalido.");
                        }
                    }
                    break;
                case 6:
                    System.out.println("Voltando ao menu inicial...");
                    break;
                default:
                    System.out.println("Opcao invalida.");
            }
        } while (opcao != 6);
    }
}