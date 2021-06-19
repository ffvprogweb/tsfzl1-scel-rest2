package com.fatec.scel.ts;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import com.fatec.scel.po.PaginaCadastrarLivro;
import com.fatec.scel.po.PaginaLogin;

class REQ01CadastrarLivroTS {
	private WebDriver driver;

	@BeforeEach
	public void setup() {
		System.setProperty("webdriver.chrome.driver", "browserDriver/chromedriver.exe");
		driver = new ChromeDriver();
		driver.get("https://ts-scel.herokuapp.com");
	}

	@AfterEach
	public void tearDown() {
		driver.quit();
	}

	@Test
	void ct01_quando_dados_validos_retorna_livro_cadastrado() {
		driver.findElement(By.name("username")).click();
		driver.findElement(By.name("username")).sendKeys("jose");
		driver.findElement(By.name("password")).sendKeys("123");
		driver.findElement(By.cssSelector("button")).click();
		// seleciona a opção do menu livros
		driver.findElement(By.linkText("Livros")).click();
		espera();
		driver.findElement(By.id("isbn")).click();
		driver.findElement(By.id("isbn")).sendKeys("2222");
		driver.findElement(By.id("autor")).click();
		driver.findElement(By.id("autor")).sendKeys("Sommerville");
		driver.findElement(By.id("titulo")).click();
		driver.findElement(By.id("titulo")).sendKeys("Engenharia de Software");
		driver.findElement(By.cssSelector(".btn:nth-child(1)")).click();
		assertEquals("Sommerville", driver.findElement(By.cssSelector("tr:nth-child(2) > td:nth-child(4)")).getText());
		driver.findElement(By.cssSelector("tr:nth-child(2) .delete")).click();
	}
	@Test
	void ct02_quando_livro_ja_cadastrado_retorna_erro() {
		driver.findElement(By.name("username")).click();
		driver.findElement(By.name("username")).sendKeys("jose");
		driver.findElement(By.name("password")).sendKeys("123");
		driver.findElement(By.cssSelector("button")).click();
		driver.findElement(By.linkText("Livros")).click();
		espera();
		driver.findElement(By.id("isbn")).click();
		driver.findElement(By.id("isbn")).sendKeys("1111");
		driver.findElement(By.id("autor")).click();
		driver.findElement(By.id("autor")).sendKeys("Pressman");
		driver.findElement(By.id("titulo")).sendKeys("Engenharia de Software");
		driver.findElement(By.cssSelector(".btn:nth-child(1)")).click();
		assertEquals("Livro ja cadastrado", driver.findElement(By.cssSelector(".text-danger")).getText());
	}
	
	@Test
	public void ct03_quando_dados_validos_retorna_livro_cadastrado() {
		PaginaLogin paginaLogin = new PaginaLogin(driver);
		paginaLogin.login("jose", "123");
		PaginaCadastrarLivro paginaLivro = new PaginaCadastrarLivro(driver);
		paginaLivro.cadastrar("3333", "Teste de Software", "Delamaro");
		assertEquals("Teste de Software", paginaLivro.getResultado());
		paginaLivro.excluirRegistro();
	}
	public void espera() {
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
