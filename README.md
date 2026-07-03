
Sunland Hotel Manager

Sistema de gerenciamento de hotel desenvolvido em Java utilizando Programação Orientada a Objetos.


Como executar:

Compile o projeto:

```bash
javac -d bin src\models\*.java src\exceptions\*.java src\exceptions\*.java src\Main\Main.java 
```
Execute:

```bash
java -cp bin Main.Main
```

A documentação completa está disponível na pasta `docs/`.

Após clonar o projeto, basta abrir:
```text
docs/index.html
```
para acessar a documentação gerada pelo Javadoc.

Conceitos de POO utilizados

- Encapsulamento
- Herança
- Polimorfismo
- Abstração
- Interfaces
- Classes abstratas
- Tratamento de exceções
- Coleções (`ArrayList`)

Projeto desenvolvido para fins academicos por Emanuel Victor Barros Bezerra.




Diagrama de Classes pode ser visualizado atraves do PNG ou usando o codigo do PlantUml


<img width="2922" height="1372" alt="Diagrama de Classes SHM-POO" src="https://github.com/user-attachments/assets/1592ab76-af3e-436b-9266-2a420d14c881" />
     
            @startuml
            skinparam classAttributeIconSize 0
                  
            interface Reservavel {
              + reservar() : void
              + liberar() : void
              + estaDisponivel() : boolean
          }
          
          abstract class Quarto {
              - numero : int
              - precoBase : double
              - capacidade : int
              - status : String
              + Quarto(numero : int, precoBase : double, capacidade : int)
              + getNumero() : int
              + getPrecoBase() : double
              + getCapacidade() : int
              + getStatus() : String
              + setNumero(numero : int) : void
              + setPrecoBase(precoBase : double) : void
              + setCapacidade(capacidade : int) : void
              - setStatus(status : String) : void
              + estaDisponivel() : boolean
              + reservar() : void
              + liberar() : void
              + {abstract} calcularDiaria() : double
              + {abstract} exibirDescricao() : String
          }
          
          
          class QuartoSimples {
              - possuiArCondicionado : boolean
              + QuartoSimples(numero : int, precoBase : double, capacidade : int, possuiArCondicionado : boolean)
              + setPossuiArCondicionado(possuiArCondicionado : boolean) : void
              + isPossuiArCondicionado() : boolean
              + calcularDiaria() : double
              + exibirDescricao() : String
          }
          
          class QuartoDuplo {
              - tipoCama : String
              - possuiVaranda : boolean
              + QuartoDuplo(numero : int, precoBase : double, capacidade : int, tipoCama : String, possuiVaranda : Boolean)
              + setTipoCama(tipoCama : String) : void
              + getTipoCama() : String
              + setPossuiVaranda(possuiVaranda : boolean) : void
              + isPossuiVaranda() : boolean
              + calcularDiaria() : double
              + exibirDescricao() : String
          }
          
          class Suite {
              - tipoSuite : String
              - {static} PRESIDENCIAL : String
              - {static} EXECUTIVA : String
              + Suite(numero : int, precoBase : double, capacidade : int, tipoSuite : String)
              + getTipoSuite() : String
              + setTipoSuite(tipoSuite : String) : void
              + calcularDiaria() : double
              + exibirDescricao() : String
          }
          
          
          class Hospede {
              - nome : String
              - cpf : String
              - email : String
              + Hospede(nome : String, cpf : String, email : String)
              + {static} validaCpf(cpf : String) : boolean
              + {static} validaEmail(email : String) : boolean
              + setNome(nome : String) : void
              + setCpf(cpf : String) : void
              + setEmail(email : String) : void
              + toString() : String
              + getNome() : String
              + getCpf() : String
              + getEmail() : String
          }
          
          class Reserva {
              - hospede : Hospede
              - quarto : Quarto
              - qtdeDiarias : int
              - estadoReserva : String
              + Reserva(hospede : Hospede, quarto : Quarto, qtdeDiarias : int)
              + confirmaReserva() : void
              + setHospede(hospede : Hospede) : void
              + setQuarto(quarto : Quarto) : void
              + setQtdeDiarias(qtdeDiarias : int) : void
              + getHospede() : Hospede
              + getQuarto() : Quarto
              + getQtdeDiarias() : int
              + getEstadoReserva() : String
              + realizarCheckIn() : void
              + realizarCheckOut() : void
              + calcularValorTotal() : double
              + exibirDescricao() : String
          }
          
          class Hotel {
              - quartos : List<Quarto>
              - hospedes : List<Hospede>
              - reservas : List<Reserva>
              + Hotel()
              + adicionarQuarto(quarto : Quarto) : void
              + adicionarHospede(hospede : Hospede) : void
              + criarReserva(hospede : Hospede, quarto : Quarto, qtdeDiarias : int) : void
              + buscarQuartoporNumero(numero : int) : Quarto
              + buscarHospedePorCpf(cpf : String) : Hospede
              + buscarReservaPorCpf(cpf : String) : Reserva
              + excluirHospede(hospede : Hospede) : void
              + excluirReserva(reserva : Reserva) : void
              + excluirQuarto(quarto : Quarto) : void
          }
          
          exception DadosInexistentesException {
              + DadosInexistentesException(msg : String)
          }
          exception DadosInvalidosException {
              + DadosInvalidosException(msg : String)
          }
          exception DadosNaoInformadosException {
              + DadosNaoInformadosException(msg : String)
          }
          
          
            Reservavel <|.. Quarto : Implementa
            Quarto <|-- QuartoSimples : Herda
            Quarto <|-- QuartoDuplo : Herda
            Quarto <|-- Suite : Herda
          
            Reserva "0..*" --> "1" Hospede : Associado a
            Reserva "0..*" --> "1" Quarto : Alocado em
          
            Hotel "1" *--> "0..*" Quarto : Composto por
            Hotel "1" *--> "0..*" Hospede : Composto por
            Hotel "1" *--> "0..*" Reserva : Composto por
          
            Hotel ..> DadosInexistentesException : <<throws>>

          }
          Hotel ..> DadosNaoInformadosException : <<throws>>
          Hospede ..> DadosInvalidosException : <<throws>>


@enduml
