package adminconsole.gestores;

import models.Model;
import models.pessoas.Aluno;

import java.rmi.RemoteException;

import static adminconsole.AdminConsole.*;

public class Pessoa {

    public static void insert() throws RemoteException {
        getProperty(rmi.query("Departamentos","*", " ") + "Insira o ID da faculdade à qual pretende adicionar uma pessoa: ",
                "Por favor insira um ID válido!",
                () -> {
                    try {
                        return (departamento = (models.organizacoes.Departamento) rmi.get("Departamentos", "ID = " + sc.nextInt())) == null;
                    } catch (RemoteException e) {
                        e.printStackTrace();
                        return true;
                    }
                });

        getProperty(
                "Escolha o tipo de pessoa a inserir:\n" +
                        "1 - Aluno\n" +
                        "2 - Docente\n" +
                        "3 - Funcionário\n",
                "Por favor insira um número correspondente a um dos tipos disponíveis.\n",
                () -> !contains(new int[]{1, 2, 3}, r1 = sc.nextInt()));

        if (r1 == 1) {

        } else if (r1 == 2) {

        } else {



        getProperty("Insira o Nome: ",
                "Por favor insira um nome só com letras!",
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

        if (pessoa.isAluno()) {
            aluno = (Aluno) pessoa;

            getProperty("Insira o Número de Aluno: ",
                    "Por favor insira um número de aluno com apenas 10 digitos.\n",
                    () -> aluno.setNumeroAluno(sc.nextLine()));

            getProperty("Insira o Curso: ",
                    "Por favora insira o nome do curso usando apenas letras.\n",
                    () -> aluno.setCurso(sc.nextLine()));

        }





        rmi.insert(pessoa);

    }
}
