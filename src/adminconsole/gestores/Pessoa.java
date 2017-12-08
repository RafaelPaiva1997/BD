package adminconsole.gestores;

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

        getProperty("Insira o Nome: ",
                "Por favor insira um nome só com letras!",
                () -> !departamento.setNome(sc.nextLine()));

        rmi.insert(departamento);

        departamento = (models.organizacoes.Departamento) rmi.get("Departamentos", "nome = '" + departamento.getNome() + "'");
        rmi.connect(faculdade, departamento);
    }
}
