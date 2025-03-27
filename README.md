📌 # Projeto Web Scraping - Download e Compactação de Anexos

Este projeto realiza o download automático de arquivos disponibilizados pelo site do governo e os compacta em um arquivo ZIP. 
Ele foi desenvolvido utilizando Java e Selenium WebDriver para automatizar o processo.

📂 Estrutura do Projeto

O projeto está localizado em:
/src/controller/WebScraping.java

A classe WebScraping contém toda a lógica de:

* Acesso ao site oficial da ANS;
* Download dos anexos I e II nos formatos PDF e XLSX;
* Compactação dos arquivos baixados em um único ZIP.

🚀 Tecnologias Utilizadas

Java (linguagem principal)

Selenium WebDriver (para automação de navegador)

Edge WebDriver (para execução no navegador Microsoft Edge)

Java I/O e NIO (para manipulação de arquivos)

ZIP API (para compactação dos arquivos).

📥 Como Usar

Requisitos

Java 8 ou posterior instalado

Edge WebDriver (colocado em resources/msedgedriver.exe)

Executar o Código

1 - Clone este repositório:https://github.com/Harissis/PROCESSO_SELETIVO_WEB_SCRAPING

2 - Navegue até a pasta do projeto: cd src/controller

3 - Compile e execute o código: WebScraping.java

📑 O que este código faz?

Acessa o site da ANS.

Baixa os arquivos PDF e XLSX automaticamente.

Armazena os arquivos na pasta Downloads do usuário.

Compacta tudo em um único arquivo ZIP.
