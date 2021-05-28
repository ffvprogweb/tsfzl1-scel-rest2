package com.fatec.scel.po;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class PaginaCadastrarLivro {
	private WebDriver driver;
	private By isbnBy = By.id("isbn");
	private By autorBy = By.id("autor");
	private By tituloBy = By.id("titulo");
	private By btnCadastrarLivroBy = By.cssSelector(".btn:nth-child(1)");
    private By resultado = By.cssSelector("tr:nth-child(2) > td:nth-child(4)");
    private By btnExcluir = By.cssSelector("tr:nth-child(2) .delete");
    private By linkMenuLivros = By.linkText("Livros");
    
    public PaginaCadastrarLivro(WebDriver driver) {
    	this.driver = driver;
    }
    
    public PaginaCadastrarLivro cadastrar (String isbn, String autor, String titulo) {
    	driver.findElement(linkMenuLivros).click();
    	driver.findElement(isbnBy).click();
    	driver.findElement(isbnBy).sendKeys(isbn);
    	driver.findElement(autorBy).sendKeys(autor);
    	driver.findElement(tituloBy).sendKeys(titulo);
    	driver.findElement(btnCadastrarLivroBy).click();
    	return new PaginaCadastrarLivro(driver);
    }
    public String getResultado() {
    	return driver.findElement(resultado).getText();
    }
    
    public void excluirRegistro() {
    	driver.findElement(btnExcluir).click();
    }
}
