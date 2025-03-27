package controller;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import java.io.*;
import java.net.URL;
import java.nio.file.*;
import java.util.Collections;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class WebScraping {

    public static void main(String[] args) {
        coletadados();
    }

    private static void coletadados() {
        System.setProperty("webdriver.edge.driver", "resources/msedgedriver.exe"); // Definir o caminho do driver

        EdgeOptions options = new EdgeOptions(); // Usado para evitar falhas na execução
        options.addArguments("--no-sandbox"); // Ignora isolamento de segurança
        options.addArguments("--disable-dev-shm-usage"); // Usa /tmp em vez de /dev/shm
        options.addArguments("--disable-blink-features=AutomationControlled"); // Evita detecção de sites
        options.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));
        options.setExperimentalOption("useAutomationExtension", null);
        options.addArguments("window-size=1600,800");

        WebDriver driver = new EdgeDriver(options); // Criação do driver do Edge

        try {
            driver.get("https://www.gov.br/ans/pt-br/acesso-a-informacao/participacao-da-sociedade/atualizacao-do-rol-de-procedimentos");
            Thread.sleep(3000); // Aguarda carregamento da página

            // URLs para os anexos
            String anexo1PDF = "https://www.gov.br/ans/pt-br/acesso-a-informacao/participacao-da-sociedade/atualizacao-do-rol-de-procedimentos/Anexo_I_Rol_2021RN_465.2021_RN627L.2024.pdf";
            String anexo2PDF = "https://www.gov.br/ans/pt-br/acesso-a-informacao/participacao-da-sociedade/atualizacao-do-rol-de-procedimentos/Anexo_II_DUT_2021_RN_465.2021_RN628.2025_RN629.2025.pdf";
            String anexo1XLSX = "https://www.gov.br/ans/pt-br/acesso-a-informacao/participacao-da-sociedade/atualizacao-do-rol-de-procedimentos/Anexo_I_Rol_2021RN_465.2021_RN627L.2024.xlsx";

            System.out.println("Iniciando download dos arquivos...");
            baixarArquivo(anexo1PDF, "Anexo_I.pdf");
            baixarArquivo(anexo2PDF, "Anexo_II.pdf");
            baixarArquivo(anexo1XLSX, "Anexo_I_Rol_2021.xlsx");

            System.out.println("Downloads completos!");

            // Compactação dos arquivos em um único arquivo ZIP
            compactarArquivos();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            driver.quit();
        }
    }

    private static void baixarArquivo(String fileURL, String fileName) {
        try (BufferedInputStream in = new BufferedInputStream(new URL(fileURL).openStream());
             FileOutputStream fileOutputStream = new FileOutputStream(Paths.get(System.getProperty("user.home"), "Downloads", fileName).toString())) {

            byte dataBuffer[] = new byte[1024];
            int bytesRead;
            while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                fileOutputStream.write(dataBuffer, 0, bytesRead);
            }
            System.out.println("Arquivo " + fileName + " baixado com sucesso!");

        } catch (Exception e) {
            System.out.println("Erro ao baixar o arquivo: " + fileName);
            e.printStackTrace();
        }
    }

    private static void compactarArquivos() {
        try {
            // Defina o nome do arquivo ZIP
            String zipFileName = Paths.get(System.getProperty("user.home"), "Downloads", "Anexos_Compactados.zip").toString();

            // Criar o fluxo de saída do arquivo ZIP
            try (ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zipFileName))) {
                adicionarArquivoNoZip(zos, "Anexo_I.pdf");
                adicionarArquivoNoZip(zos, "Anexo_II.pdf");
                adicionarArquivoNoZip(zos, "Anexo_I_Rol_2021.xlsx");
            }

            System.out.println("Arquivos compactados com sucesso em: " + zipFileName);
        } catch (IOException e) {
            System.out.println("Erro ao compactar os arquivos.");
            e.printStackTrace();
        }
    }

    private static void adicionarArquivoNoZip(ZipOutputStream zos, String fileName) {
        try {
            File file = new File(Paths.get(System.getProperty("user.home"), "Downloads", fileName).toString());
            FileInputStream fis = new FileInputStream(file);

            // Adicionando o arquivo ao ZIP
            zos.putNextEntry(new ZipEntry(fileName));
            byte[] buffer = new byte[1024];
            int length;
            while ((length = fis.read(buffer)) >= 0) {
                zos.write(buffer, 0, length);
            }
            zos.closeEntry();
            fis.close();
            System.out.println("Arquivo " + fileName + " adicionado ao ZIP.");
        } catch (IOException e) {
            System.out.println("Erro ao adicionar arquivo no ZIP: " + fileName);
            e.printStackTrace();
        }
    }
}
