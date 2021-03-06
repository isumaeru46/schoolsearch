package br.iesb.schoolsearch.schoolsearch.models;

import java.io.Serializable;

public class EnderecoEscolaModel implements Serializable{

    private String cep;
    private String descricao;
    private String bairro;
    private String municipio;
    private String uf;

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
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


    @Override
    public String toString() {
        String logradouro = getDescricao() != null ? getDescricao().trim() : "";
        String bairro = getBairro() != null ? getBairro().trim() : "";
        String cidade = getMunicipio() != null ? getMunicipio().trim() : "";
        String uf = getUf() != null ? getUf().trim() : "";
        String cep = getCep() != null ? getCep().trim() : "";

        return "Logradouro: " + logradouro + "\nBairro: " + bairro + "\nCidade: " + cidade + " - " + uf + "\nCep: " + cep;
    }
}
