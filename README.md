# Sistema de Gerenciamento de Adoção e Doação de Animais

Este projeto é um sistema informatizado para o gerenciamento de adoção e doação de animais de estimação, desenvolvido em Java utilizando o paradigma de Orientação a Objetos (POO) e persistência em banco de dados relacional.

## Motivação e Contexto

A motivação para a escolha do tema surgiu da vontade de trabalhar com uma lógica de negócio concreta e real, onde as relações entre as classes fossem naturais e fluidas. 

O problema abordado reside na dificuldade frequente que abrigos e petshops enfrentam com o controle manual de animais disponíveis, o histórico de doadores e o acompanhamento das solicitações feitas por interessados.

## A Solução

Para solucionar esse problema, o sistema simula o fluxo real dessas instituições por meio de uma plataforma com hierarquia de usuários e responsabilidades bem definidas. O acesso é separado entre dois perfis principais:

* Cliente: Possui autonomia para realizar a doação de pets, visualizar os animais disponíveis no catálogo e emitir solicitações de adoção.
* Gerente: Atua como administrador do sistema, detendo permissões específicas para gerenciar o inventário (cadastrar, editar e remover animais) e para analisar cada solicitação de adoção pendente, podendo aprová-la ou reprová-la.

Esta dinâmica resulta em um projeto rico, com entidades que possuem estados que mudam ao longo do tempo e regras de validação rigorosas que precisam ser respeitadas em diversas camadas do código. Toda a persistência de dados gerada por esse fluxo é feita de forma confiável utilizando um banco de dados relacional.

---

## Ferramentas e Tecnologias Utilizadas

* Linguagem de Programação: Java
* Banco de Dados: MySQL (utilizado para a persistência das entidades)
* IDE utilizada: IntelliJ IDEA Community Edition

### Bibliotecas e APIs Nativas:
* java.sql.* (JDBC): Para conexão e manipulação do banco de dados (Statements, ResultSets).
* java.time.LocalDate: Para gerenciamento atualizado das datas de cadastro e solicitações.
* java.util.* (List, ArrayList, Scanner): Para estruturação de dados em memória e entrada de usuários via console.

---

## Estrutura do Projeto

O projeto foi organizado em pacotes lógicos que obedecem à seguinte estrutura organizacional:

* Domínio / Entidades: Classes que representam o núcleo do negócio, como Pessoa, Pet, Cliente e Gerente.
* Enumerações: StatusSolicitacao (para controle do fluxo de aprovação).
* Repositórios (Persistência): Classes responsáveis pelas operações de CRUD e comunicação com o banco de dados (ConexaoBanco, *repositorio).
* Interface / Main: A classe Main atua como a interface de terminal interativa com o usuário.

---

## Como Executar o Projeto

1. Certifique-se de ter o Java JDK instalado na sua máquina.
2. Certifique-se de ter o MySQL instalado e configurado.
3. Clone o repositório:
   ```bash
   git clone [https://github.com/edu4rd0luiz/Projeto_final_poo.git](https://github.com/edu4rd0luiz/Projeto_final_poo.git)
