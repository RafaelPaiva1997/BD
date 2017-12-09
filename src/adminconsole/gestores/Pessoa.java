package adminconsole.gestores;

import models.Model;
import models.pessoas.Aluno;
import models.pessoas.Docente;
import models.pessoas.Funcionario;

import javax.print.Doc;
import java.rmi.RemoteException;
import java.util.function.BooleanSupplier;

import static adminconsole.AdminConsole.*;

public class Pessoa {

    public static void menu() {
        gerir("MENU PESSOAS\n" +
                        "O que pretende fazer?\n" +
                        "1 - Adicionar\n" +
                        "2 - Editar\n" +
                        "3 - Remover\n" +
                        "4 - Listar\n" +
                        "5 - Voltar\n",
                "Por favor insira um número correspondente a uma das opcções disponíveis.\n",
                new int[]{1, 2, 3, 4, 5},
                new BooleanSupplier[]{
                        () -> {
                            try {
                                insert();
                                return true;
                            } catch (RemoteException e) {
                                e.printStackTrace();
                                return false;
                            }
                        },
                        () -> {
                            try {
                                update();
                                return true;
                            } catch (RemoteException e) {
                                e.printStackTrace();
                                return false;
                            }
                        },
                        () -> {
                            try {
                                delete();
                                return true;
                            } catch (RemoteException e) {
                                e.printStackTrace();
                                return false;
                            }
                        },
                        () -> {
                            try {
                                System.out.print(rmi.query("Pessoas", "*", ""));
                                return true;
                            } catch (RemoteException e) {
                                e.printStackTrace();
                                return false;
                            }
                        }
                });
    }


    public static void escolheGenero() {
        getProperty("Escolha um Género:\n" +
                        "1 - Masculino\n" +
                        "2 - Femenino\n" +
                        "3 - Outro\n",
                "Por favor insira um número correspondente a um dos géneros disponíveis.\n",
                () -> !contains(new int[]{1, 2, 3}, (r1 = sc.nextInt())));

        try {
            switch (r1) {
                case 1:
                    pessoa.setGenero("Masculino");
                    break;

                case 2:
                    pessoa.setGenero("Feminino");
                    break;

                case 3:
                    pessoa.setGenero("Outro");
                    break;
            }

            sc.nextLine();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void insert() throws RemoteException {
        String s;

        getProperty(rmi.query("Departamentos", "*", " ") + "Insira o ID da faculdade à qual pretende adicionar uma pessoa: ",
                "Por favor insira um ID válido!\n",
                () -> {
                    try {
                        return (departamento = (models.organizacoes.Departamento) rmi.get("Departamentos", "ID = " + sc.nextInt())) == null;
                    } catch (RemoteException e) {
                        e.printStackTrace();
                        return true;
                    }
                });

        sc.nextLine();

        pessoa = new models.pessoas.Pessoa();

        getProperty("Insira o Nome: ",
                "Por favor insira um nome só com letras!\n",
                () -> !pessoa.setNome(sc.nextLine()));

        getProperty("Insira o Username: ",
                "Por favor insira um username com entre 8 a 20 caracteres que não esteja em uso.\n",
                () -> !pessoa.setUsername(sc.nextLine()));

        getProperty("Insira a Password: ",
                "Por favor insira uma password entre 8 a 20 caracteres.\n",
                () -> !pessoa.setPassword(sc.nextLine()));


        getProperty("Insira o número de telemóvel: ",
                "Por favor insira um telemóvel com apenas 9 dígitos.\n",
                () -> pessoa.setTelemovel(sc.nextLine()));


        getProperty("Insira uma Morada: ",
                "Por favor insira pelo menos 1 carater na morada.\n",
                () -> pessoa.setMorada(sc.nextLine()));


        getProperty("Insira o Código Postal: ",
                "Por favor insira um código postal neste formato '0000-000.\n",
                () -> pessoa.setCodigo_postal(sc.nextLine()));


        getProperty("Insira Localidade: ",
                "Por favor insira um telemóvel com pelo menos 1 carater.\n",
                () -> pessoa.setLocalidade(sc.nextLine()));


        getProperty("Insira o número do Cartão de Cidadão: ",
                "Por favor insira um número de cartão de cidadão com apenas 8 digítos.\n",
                () -> pessoa.setNumero_cc(sc.nextLine()));

        pessoa.setValidade_cc(Data.editData("a validade do CC", new models.Data()).export());
        pessoa.setData_nascimento(Data.editData("a data de nascimento", new models.Data()).export());

        escolheGenero();

        getProperty(
                "Escolha o tipo de pessoa a inserir:\n" +
                        "1 - Aluno\n" +
                        "2 - Docente\n" +
                        "3 - Funcionário\n",
                "Por favor insira um número correspondente a um dos tipos disponíveis.\n",
                () -> !contains(new int[]{1, 2, 3}, r1 = sc.nextInt()));

        sc.nextLine();

        if (r1 == 1) {
            aluno = new Aluno(pessoa);

            getProperty("Insira o Número de Aluno: ",
                    "Por favor insira um número de aluno com apenas 10 digitos.\n",
                    () -> aluno.setNumeroAluno(sc.nextLine()));

            getProperty("Insira o Curso: ",
                    "Por favora insira o nome do curso usando apenas letras.\n",
                    () -> aluno.setCurso(sc.nextLine()));

            rmi.insert(aluno);
        } else if (r1 == 2) {
            docente = new Docente(pessoa);

            getProperty("Insira o Cargo: ",
                    "Por favora insira o cargo usando apenas letras.\n",
                    () -> docente.setCargo(sc.nextLine()));

            rmi.insert(docente);
        } else {
            funcionario = new Funcionario(pessoa);

            getProperty("Insira a Função: ",
                    "Por favora insira a função usando apenas letras.\n",
                    () -> funcionario.setFuncao(sc.nextLine()));

            rmi.insert(funcionario);
        }
    }

    public static void update() throws RemoteException {
        getProperty(rmi.query("Pessoas","*", "") + "Insira o ID da pessoa a editar: ",
                "Por favor insira um ID válido!",
                () -> {
                    try {
                        return (pessoa = (models.pessoas.Pessoa) rmi.get("Pessoas", "ID = " + sc.nextInt())) == null;
                    } catch (RemoteException e) {
                        e.printStackTrace();
                        return true;
                    }
                });

        try {

            sc.nextLine();

            String s1 = "\nEscolha a propriedade a editar:\n" +
                    "Nome - Username - Password - Telemóvel\n" +
                    "Morada - Código Postal - Localidade\n" +
                    "Número CC - Validade CC - Género\n" +
                    "Data Nascimento - ";

            if (pessoa.getTipo().equals("aluno"))
                s1 = s1 + "Nº Aluno - Curso\n";
            else if (pessoa.getTipo().equals("docente"))
                s1 = s1 + "Cargo\n";
            else
                s1 = s1 + "Função\n";

            getProperty( s1,
                    "Por favor insira uma característica correspondente a uma das disponíveis.\n",
                    () -> {
                        try {
                            return !(contains(new String[]{
                                    "nome",
                                    "username",
                                    "password",
                                    "nº telemóvel",
                                    "nº telemovel",
                                    "no telemóvel",
                                    "no telemovel",
                                    "morada",
                                    "código postal",
                                    "codigo postal",
                                    "localidade",
                                    "número c.c.",
                                    "numero c.c.",
                                    "número cc",
                                    "numero cc",
                                    "validade c.c.",
                                    "validade cc",
                                    "género",
                                    "genero",
                                    "data nascimento",
                                    "mesas de voto",
                                    "listas",
                                    "voto"
                            }, r2 = sc.nextLine()) ||
                                    pessoa.getTipo().equals("aluno") && contains(new String[]{
                                            "nºaluno",
                                            "no aluno",
                                            "curso"
                                    }, r2) ||
                                    pessoa.getTipo().equals("docente") && contains(new String[]{
                                            "função",
                                            "funçao",
                                            "funcão",
                                            "funcao"
                                    }, r2) ||
                                    pessoa.getTipo().equals("funcionario") && r2.toLowerCase().equals("cargo"));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return false;
                    });
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        switch (r2.toLowerCase()) {
            case "nome":
                getProperty("Por favor insira um nome só com letras!\n",
                        () -> !pessoa.update("nome", editProperty("Nome", pessoa.getNome())));
                rmi.update(pessoa);
                break;

            case "username":
                getProperty("Por favor insira um username com entre 8 a 20 caracteres que não esteja em uso.\n",
                        () -> !pessoa.update("username", editProperty("Username", pessoa.getUsername())));
                break;
            case "password":
                getProperty("Por favor insira uma password entre 8 a 20 caracteres.\n",
                        () -> !pessoa.update("password", editProperty("Password", pessoa.getPassword())));
                break;
            case "nº telemóvel":
            case "nº telemovel":
            case "no telemóvel":
            case "no telemovel":
                getProperty("Por favor insira um telemóvel com apenas 9 dígitos.\n",
                        () -> !pessoa.update("telemovel", editProperty("Nr telemovel", String.valueOf(pessoa.getTelemovel()))));
                break;

            case "morada":
                getProperty("Por favor insira pelo menos 1 carater na morada.\n",
                        () -> !pessoa.update("morada", editProperty("Morada", pessoa.getMorada())));
                break;
            case "codigo postal":
                getProperty("Por favor insira um código postal neste formato '0000-000.\n",
                        () -> !pessoa.update("codigo_postal", editProperty("Codigo Postal", pessoa.getCodigo_postal())));
                break;
            case "localidade":
                getProperty("Por favor insira um telemóvel com pelo menos 1 carater.\n",
                        () -> !pessoa.update("localidade", editProperty("Localidade", pessoa.getLocalidade())));
                break;

            case "número c.c.":
            case "numero c.c.":
            case "número cc":
            case "numero cc":
                getProperty("Por favor insira um número de cartão de cidadão com apenas 8 digítos.\n",
                        () -> !pessoa.update("numero_cc", editProperty("Numero CC", String.valueOf(pessoa.getNumero_cc()))));
                break;

            case "validade c.c.":
            case "validade cc":

                break;

            case "género":
            case "genero":
                try {
                    escolheGenero();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                escolheGenero();
                break;

            case "data nascimento":

                break;
            case "nº aluno":
            case "no aluno":
                aluno = (Aluno) rmi.get("Alunos", "WHERE pessoa_id = " + pessoa.getId());
                getProperty("Por favor insira um número de aluno com apenas 10 digitos.\n",
                        () -> !aluno.update("numero_aluno", editProperty("Nº Aluno", String.valueOf(aluno.getNumero_aluno()))));
                rmi.update(aluno);
                break;
            case "curso":
                aluno = (Aluno) rmi.get("Alunos", "WHERE pessoa_id = " + pessoa.getId());
                getProperty("Por favor insira um curso com pelo menos 1 caractér.\n",
                        () -> aluno.update("curso",editProperty("Curso", aluno.getCurso())));

            case "cargo":
                docente = (Docente) rmi.get("Docentes", "WHERE pessoa_id = " + pessoa.getId());
                getProperty("Por favor insira um cargo com pelo menos 1 caractér.\n",
                        () ->docente.update("cargo",editProperty("Cargo", docente.getCargo())));

                break;
            case "função":
            case "funçao":
            case "funcão":
            case "funcao":
                docente = (Docente) rmi.get("Docentes", "WHERE pessoa_id = " + pessoa.getId());
                getProperty("Por favor insira um cargo com pelo menos 1 caractér.\n",
                        () -> funcionario.update("Funcionarios", editProperty("Funcao", funcionario.getFuncao())));
                break;
            case "mesa de voto":

                break;

            case "listas":

                break;

            case "voto":

                break;

        }

        rmi.update(pessoa);
    }



    public static void delete() throws RemoteException {
        getProperty(rmi.query("Pessoas", "*", "") + "Insira o ID do departamento a remover: ",
                "Por favor insira um ID válido!",
                () -> {
                    try {
                        return (pessoa = (models.pessoas.Pessoa) rmi.get("Pessoas", "ID = " + sc.nextInt())) == null;
                    } catch (RemoteException e) {
                        e.printStackTrace();
                        return true;
                    }
                });

        rmi.delete(pessoa);
    }
}
