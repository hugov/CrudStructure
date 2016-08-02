/**
 * 
 */
package br.com.digicon.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Vitor Hugo Oliveira
 * 
 */
public class RedeVenda implements Serializable {

	private static final long serialVersionUID = 7164561847528545184L;
	private Long idRedeVenda;
	private Integer tipoContrato;
	private String razaoSocial;
	private String cnpjCpf;
	private String responsavel;
	private String telefone;
	private String logradouro;
	private Long numero;
	private String complemento;
	private String bairro;
	private String cep;
	private String municipio;
	private String uf;
	private Date dataInicioVigencia;
	private Date dataFinalVigencia;
	private Integer status;
	private Integer quantidadeMaximaTerminal;
	private Integer exibeSaldoTerminal;

	public Long getIdRedeVenda() {
		return idRedeVenda;
	}

	public void setIdRedeVenda(Long idRedeVenda) {
		this.idRedeVenda = idRedeVenda;
	}

	public Integer getTipoContrato() {
		return tipoContrato;
	}

	public void setTipoContrato(Integer tipoContrato) {
		this.tipoContrato = tipoContrato;
	}

	public String getRazaoSocial() {
		return razaoSocial;
	}

	public void setRazaoSocial(String razaoSocial) {
		this.razaoSocial = razaoSocial;
	}

	public String getCnpjCpf() {
		return cnpjCpf;
	}

	public void setCnpjCpf(String cnpjCpf) {
		this.cnpjCpf = cnpjCpf;
	}

	public String getResponsavel() {
		return responsavel;
	}

	public void setResponsavel(String responsavel) {
		this.responsavel = responsavel;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public Long getNumero() {
		return numero;
	}

	public void setNumero(Long numero) {
		this.numero = numero;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getMunicipio() {
		return municipio;
	}

	public void setMunicipio(String municipio) {
		this.municipio = municipio;
	}

	public String getUf() {
		return uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}

	public Date getDataInicioVigencia() {
		return dataInicioVigencia;
	}

	public void setDataInicioVigencia(Date dataInicioVigencia) {
		this.dataInicioVigencia = dataInicioVigencia;
	}

	public Date getDataFinalVigencia() {
		return dataFinalVigencia;
	}

	public void setDataFinalVigencia(Date dataFinalVigencia) {
		this.dataFinalVigencia = dataFinalVigencia;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getQuantidadeMaximaTerminal() {
		return quantidadeMaximaTerminal;
	}

	public void setQuantidadeMaximaTerminal(Integer quantidadeMaximaTerminal) {
		this.quantidadeMaximaTerminal = quantidadeMaximaTerminal;
	}

	public Integer getExibeSaldoTerminal() {
		return exibeSaldoTerminal;
	}

	public void setExibeSaldoTerminal(Integer exibeSaldoTerminal) {
		this.exibeSaldoTerminal = exibeSaldoTerminal;
	}

}
