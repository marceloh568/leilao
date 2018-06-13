package br.com.leilao.control;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.validator.ValidatorException;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

import br.com.leilao.comum.exception.ProjetoException;
import br.com.leilao.comum.util.Utilites;
import br.com.leilao.dao.AnimalDao;
import br.com.leilao.dao.ParcelasCompradorDao;
import br.com.leilao.dao.VendedorDao;
import br.com.leilao.model.Animal;
import br.com.leilao.model.CompraLote;
import br.com.leilao.model.CompradoresWrapper;
import br.com.leilao.model.Leilao;
import br.com.leilao.model.Lotes;
import br.com.leilao.model.ParcelasComprador;
import br.com.leilao.model.VendedorBean;
import br.com.leilao.service.AnimalService;
import br.com.leilao.service.LoteService;
import br.com.leilao.service.VendededorService;

@ManagedBean
@ViewScoped
public class LoteController implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Lotes loteBean;
	
	private Animal animalSelect, animaledit, animalAux; 
	
	private LoteService loteService;
	
	private VendedorBean vendedorSelect, compradorSelect;
	
	private AnimalService animalService;
	
	private VendededorService vendedorService;
	
	private Boolean visaoDadosAnimais; 
	
	private Boolean visaoDosDadosDoLote;
	
	private Boolean visaoDoHistoricoDeLotes = true;
	
	private Leilao homeLeilao;
	
	private String idMaxLote;
	
	private List<CompradoresWrapper> listaDeCompradoresLote;
	
	private Double valorTotalLances = 0.0d;
	
	private ParcelasCompradorDao parcelasCompradorDao;
	
	private Double valorDaParcelaDialog;
	
	private Integer numeroParcela;
	
	private List<ParcelasComprador> listaParcelasCompradorDeleteObj;
	
	private String buscaCPFVendedor;
	
	private VendedorBean novoVendedor;
	
	public LoteController() throws ProjetoException {
		loteBean = new Lotes();
		novoVendedor = new VendedorBean();
		animaledit = new Animal();
		animalAux = new Animal();
		animalSelect = new Animal();
		loteService = new LoteService();
		idMaxLote = loteService.getMaxId();
		loteBean.setNumero(idMaxLote);
		animalService = new AnimalService();
		vendedorService = new VendededorService();
		vendedorSelect = new VendedorBean();
		compradorSelect = new VendedorBean();
		parcelasCompradorDao = new ParcelasCompradorDao();
		setListaDeCompradoresLote(new ArrayList<CompradoresWrapper>());
		homeLeilao = (Leilao) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("leilao");
	}
	
    public void setNumeroLote(){
		this.loteBean.setNumero(idMaxLote + 1);
		setLoteBean(loteBean);
    }
	
	public void novoLote() {
		loteBean = new Lotes();
		setVisaoDosDadosDoLote(true);
		setVisaoDoHistoricoDeLotes(false);
		loteBean.setComissaoComprador(homeLeilao.getComissaoComprador());
		loteBean.setComissaoVendedor(homeLeilao.getComissaoVendedor());
	}
	
	public void removeCompradorLista(CompradoresWrapper compradoresWrapper) {
		this.listaDeCompradoresLote.remove(compradoresWrapper);
	}
	
	public void calcularParcelas() throws ParseException{
		listCompras();
	}
	
	public void calcularParcelas2() throws ParseException{
		listCompras();
	}
	
	public void valueChangeMethod(ValueChangeEvent e) throws ParseException{
		listCompras();
	}
	
	public void novoVendedor() throws ProjetoException {
		vendedorService.salvarVendedor(this.novoVendedor);
		this.loteBean.getListaDeVendedores().add(this.novoVendedor);
		calculaPercVendedor();
	}
	
	public void novoComprador() throws ProjetoException {
		vendedorService.salvarVendedor(this.novoVendedor);
	}
	
	public void salvarCompradorLote(Integer idVendedor, Double valorDoLance, Double valorDesconto, Integer numeroParcelas, VendedorBean comprador) throws ProjetoException {
		valorDesconto = 0.0d;
		// add atributos por comprador
		for (CompradoresWrapper compradoresWrapper : this.listaDeCompradoresLote) {
			if (compradoresWrapper.getComprador().equals(comprador)) {
				CompradoresWrapper compradorWrapper = new CompradoresWrapper();
				this.listaDeCompradoresLote.remove(compradoresWrapper);
				compradorWrapper.setComprador(comprador);
				compradorWrapper.setValorLance(valorDoLance);
				compradorWrapper.setValorDesconto(valorDesconto);
				compradorWrapper.setNumeroParcelas(numeroParcelas);
				
				List<ParcelasComprador> listaDeParcelasComprador = new ArrayList<ParcelasComprador>();
				Double parcela = (valorDoLance - valorDesconto) / numeroParcelas;
				
				for (int z = 0; z < homeLeilao.getAgruparParcelas().size(); z++) {
					for (int i = 1; i < numeroParcelas; i++) {
						if (homeLeilao.getAgruparParcelas().get(z).getAgruparParcela() == i) {
							ParcelasComprador parcelasComprador = new ParcelasComprador();
							parcelasComprador.setNumeroDaParcela(i);
							parcelasComprador.setValorDaParcela(parcela * 2);
							parcelasComprador.setComprador(compradoresWrapper.getComprador());
							listaDeParcelasComprador.add(parcelasComprador);
							numeroParcelas --;
						} else {
							ParcelasComprador parcelasComprador = new ParcelasComprador();
							parcelasComprador.setNumeroDaParcela(i);
							parcelasComprador.setValorDaParcela(parcela);
							parcelasComprador.setComprador(compradoresWrapper.getComprador());
							listaDeParcelasComprador.add(parcelasComprador);
						}
					}
					break;
				}
				
				compradorWrapper.setListParcelasComprador(listaDeParcelasComprador);
				if (listaDeParcelasComprador != null) {
					parcelasCompradorDao.save(listaDeParcelasComprador);
				}
				this.listaDeCompradoresLote.add(compradorWrapper);
			}
		}
		
		//valor total lote
		valorTotalLances = 0.0d;
		for (CompradoresWrapper compradoresWrapper : listaDeCompradoresLote) {
			valorTotalLances += compradoresWrapper.getValorLance();
		}
		
		this.loteBean.setValorTotal(valorTotalLances);
	}
	
	public void calculaTotalCompraPeloLance() throws ParseException {
		Double totalcompra = 0.0;
		Double valorLance = 0.0;
		System.out.println("loteBean.getValorLance()"+loteBean.getValorLance());
		System.out.println("loteBean.getValorLance()"+loteBean.getNumeroParcela());
		if ((this.loteBean.getNumeroParcela()!=null) && (this.loteBean.getValorLance()!=null)){
			if (loteBean.getNumeroParcela() >0){
				totalcompra = loteBean.getNumeroParcela()*loteBean.getValorLance();
				loteBean.setValorTotal(totalcompra);
			}
		}
		calcularParcelas();
	}
	
	public void calculaTotalCompraPeloTotal() throws ParseException {
		System.out.println("calc compra");
		Double totalcompra = 0.0;
		Double valorLance = 0.0;
		System.out.println("loteBean.getValorLance()"+loteBean.getValorLance());
		System.out.println("loteBean.getValorLance()"+loteBean.getNumeroParcela());
		if ((this.loteBean.getNumeroParcela()!=null) && (this.loteBean.getValorTotal()!=null)){
			if (loteBean.getNumeroParcela() >0){
				valorLance	= loteBean.getValorTotal()/ loteBean.getNumeroParcela(); 
						loteBean.setValorLance(valorLance);
			}
		}
		
		calcularParcelas();
		
	}
	
	public void calculaTotalCompraPelaParcela() throws ParseException{
		System.out.println("calc compra");
		Double totalcompra = 0.0;
		Double valorLance = 0.0;
		System.out.println("loteBean.getValorLance()"+loteBean.getValorLance());
		System.out.println("loteBean.getValorLance()"+loteBean.getNumeroParcela());
		if ((this.loteBean.getNumeroParcela()!=null) ){
			if (loteBean.getNumeroParcela() >0){
				if (this.loteBean.getValorLance()==null)
					loteBean.setValorLance(0.0);
				if (this.loteBean.getValorTotal()==null)
					loteBean.setValorTotal(0.0);
				
						loteBean.setValorTotal(loteBean.getNumeroParcela()*loteBean.getValorLance());
			}
		}
		calcularParcelas();	
	}
	
	public void listCompras() throws ParseException {
		System.out.println("vai calcular"+loteBean.getDataCompra());
		ArrayList<CompraLote> listaDeCompras = new ArrayList<CompraLote>();
		if (loteBean.getTipoCompra()!=null){
			if (loteBean.getTipoCompra().equals("D")) {
				setaDefesa();
				if ((this.loteBean.getNumeroParcela()!=null) && (this.loteBean.getValorLance()!=null)&& (this.loteBean.getDataCompra()!=null)){
					System.out.println("entrou calcular defesa");
				for(VendedorBean vend : loteBean.getListaDeVendedores()) {
					//dataIncrementada =  incrementaDatasParcelaCompra(dataAtual);
					CompraLote compra = new CompraLote();
					compra.setDataDaCompra(loteBean.getDataCompra());
					compra.setValorDoLance(loteBean.getValorLance()/loteBean.getListaDeVendedores().size());
					compra.setValorTotal(loteBean.getValorLance()/loteBean.getListaDeVendedores().size());
					compra.setComissaoComprador(loteBean.getComissaoComprador()/loteBean.getListaDeVendedores().size());
					compra.setComissaoVendedor(loteBean.getComissaoVendedor()/loteBean.getListaDeVendedores().size());
					compra.setNumeroDeParcelas(loteBean.getNumeroParcela());
					listaDeCompras.add(compra);
				}
				}
			}
			else
			{
				if ((this.loteBean.getNumeroParcela()!=null) && ((this.loteBean.getValorLance()!=null) || this.loteBean.getValorTotal()!=null) && (this.loteBean.getDataCompra()!=null)){
					Double valorParcela = this.loteBean.getValorLance();
					Date dataAtual = this.loteBean.getDataCompra();
					Date dataIncrementada = null;
					for (int i = 0; i < this.loteBean.getNumeroParcela(); i++) {
						dataIncrementada =  incrementaDatasParcelaCompra(dataAtual);
						CompraLote compra = new CompraLote();
						compra.setDataDaCompra(dataIncrementada);
						compra.setValorDoLance(valorParcela/loteBean.getListaDeCompradores().size());
						compra.setComissaoComprador(loteBean.getComissaoComprador());
						compra.setComissaoVendedor(loteBean.getComissaoVendedor());
						compra.setNumeroDeParcelas(loteBean.getNumeroParcela());
						listaDeCompras.add(compra);
						dataAtual = dataIncrementada;
					}
					}		
			}
		}
		
		System.out.println("tam foi "+listaDeCompras.size());
		this.loteBean.setListacompras(listaDeCompras);
		
	}
	
	public void setaDefesa(){
		loteBean.setComissaoVendedor(loteBean.getComissaoVendedor() + loteBean.getComissaoComprador());
		loteBean.setComissaoComprador(0.0);
		loteBean.setNumeroParcela(1);
		if (loteBean.getValorLance()!= null)
		loteBean.setValorTotal(loteBean.getValorLance());
		
	}
	
	public List<Animal> completeText(String query) throws ProjetoException {
		List<Animal> result = new ArrayList<Animal>();
		AnimalDao icdao = new AnimalDao();
		result = icdao.buscaAnimal(query);
		animalAux.setNome(query.toUpperCase());
		animalSelect.setNome(query.toUpperCase());
		return result;
    }	
	
	public List<String> completeTextPai(String nome) throws ProjetoException {
		List<String> result = new ArrayList<String>();
		AnimalDao icdao = new AnimalDao();
		result = icdao.buscaPaiAnimal(nome);
		return result;
    }	
	
	public List<String> completeTextAvoPai(String nome) throws ProjetoException {
		List<String> result = new ArrayList<String>();
		AnimalDao icdao = new AnimalDao();
		result = icdao.buscaAvoPaiAnimal(nome);
		return result;
	}	
	
	public List<String> completeTextAvoFPai(String nome) throws ProjetoException {
		List<String> result = new ArrayList<String>();
		AnimalDao icdao = new AnimalDao();
		result = icdao.buscaAvoFPaiAnimal(nome);
		return result;
	}	
	
	public List<String> completeTextMae(String nome) throws ProjetoException {
		List<String> result = new ArrayList<String>();
		AnimalDao icdao = new AnimalDao();
		result = icdao.buscaMaeAnimal(nome);
		return result;
	}	
	
	public List<String> completeTextAvoMae(String nome) throws ProjetoException {
		List<String> result = new ArrayList<String>();
		AnimalDao icdao = new AnimalDao();
		result = icdao.buscaAvoMaeAnimal(nome);
		return result;
	}	
	
	public List<String> completeTextAvoFMae(String nome) throws ProjetoException {
		List<String> result = new ArrayList<String>();
		AnimalDao icdao = new AnimalDao();
		result = icdao.buscaAvoFMaeAnimal(nome);
		return result;
	}	
	
	public List<VendedorBean> completeVendedor(String query) throws ProjetoException {
		
		 List<VendedorBean> result = new ArrayList<VendedorBean>();
	        VendedorDao icdao = new VendedorDao();
           result = icdao.buscaVendedor(query);    
	        return result;
	    }
	
	public String salvarLote() throws ProjetoException {
		if(this.loteBean.getId() == null){
			if (this.listaDeCompradoresLote.size() > 0 && this.loteBean.getListaDeVendedores().size() > 0) {
				if (loteService.salvarLote(this.loteBean, this.listaDeCompradoresLote)) {
					return "lotes?faces-redirect=true&amp;lotes=true&amp;sucesso=Lote inserido com sucesso";		
				} else {
					return "lotes?faces-redirect=true&amp;erro=Erro ao executar essa operação contate o administrador do sistema.";
				}
			} else {
				FacesContext context = FacesContext.getCurrentInstance();
		        context.addMessage(null, new FacesMessage("Alerta",  "Verifique se animais e vendedores foram incluídos!"));
				return "";
			}
		} else {
			if (this.listaDeCompradoresLote.size() > 0 && this.loteBean.getListaDeVendedores().size() > 0) {
				if (loteService.editarLote(this.loteBean)) {
					return "lotes?faces-redirect=true&amp;lotes=true&amp;sucesso=Lote editado com sucesso";		
				} else {
					return "lotes?faces-redirect=true&amp;erro=Erro ao executar essa operação contate o administrador do sistema.";
				}
			}
			else {
				FacesContext context = FacesContext.getCurrentInstance();
		        context.addMessage(null, new FacesMessage("Alerta",  "Verifique se animais e vendedores foram incluídos!"));
				return "";
			}
		}
		
		
	}
	
	public String excluirLote() {
		try {
			this.loteBean = loteService.findOne(this.loteBean.getId());
			loteService.excluirLote(this.loteBean);
			return "lotes?faces-redirect=true&amp;lotes=true&amp;sucesso=Lote excluído com sucesso";	
		} catch (ProjetoException e) {
			return "lotes?faces-redirect=true&amp;erro=Erro ao executar essa operação contate o administrador do sistema.";
		}
	}
	
	public void selecionarLote() throws ProjetoException {
		this.loteBean = loteService.findOne(this.loteBean.getId());
		if (this.loteBean.getIdLoteOrigem() != null && this.loteBean.getIdLoteOrigem() != 0) {
			RequestContext context = RequestContext.getCurrentInstance();
			context.execute("PF('editCompras').show();");
		} else {
			this.listaDeCompradoresLote = this.loteBean.getListaCompradoresWrapper();
			setVisaoDoHistoricoDeLotes(false);	
			setVisaoDosDadosDoLote(true);
		}
	}
	
	public void excluirParcela(CompradoresWrapper compradoresWrapper, Integer index) {
		for (int i = 0; i < this.listaDeCompradoresLote.size(); i++) {
			if (this.listaDeCompradoresLote.contains(compradoresWrapper)) {
				this.listaDeCompradoresLote.get(i).getListParcelasComprador().remove(index - 1);
			}
		}
	}
	
	public List<Lotes> findLotesLeilao() throws ProjetoException {
		return loteService.findLotesLeilao();
	}
	
	public String salvarAnimal() throws ProjetoException {
		if(this.animalSelect.getId() == null){
			if (animalService.salvarAnimal(this.animalSelect)) {
				return "list?faces-redirect=true&amp;sucesso=Animal cadastrado com sucesso.";		
			} else {
				return "list?faces-redirect=true&amp;erro=Erro ao executar essa operação contate o administrador do sistema.";
			}
		} else {
			if (animalService.editarAnimal(this.animalSelect)) {
				return "list?faces-redirect=true&amp;sucesso=Animal editado com sucesso.";				
			} else {
				return "list?faces-redirect=true&amp;erro=Erro ao executar essa operação contate o administrador do sistema.";
			}
		}
	}
	
	/**
	 * Adiciona animais na lista :TODO autoComplete
	 * @throws ProjetoException 
	 */
	public void adicionaVendedorLista() throws ProjetoException {
		if (vendedorSelect.getId()==null) {
	
			FacesMessage msg = null;
			msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Digite um Vendedor válido!", "");
			FacesContext.getCurrentInstance().addMessage(null, msg);
				
		}
		else
		{	
		
		
		vendedorSelect = vendedorService.findOne(vendedorSelect.getId());
		this.loteBean.getListaDeVendedores().add(this.vendedorSelect);
		calculaPercVendedor();	
		vendedorSelect = new VendedorBean();
		}
		//animalSelect = null;
	}
	
	public void adicionarVendedorListaPorCPF() throws ProjetoException {
		if (this.buscaCPFVendedor != null) {
			VendedorBean vendedorBean = new VendedorBean();
			vendedorBean = vendedorService.findByCPF(this.buscaCPFVendedor);
			if (vendedorBean != null) {
				this.loteBean.getListaDeVendedores().add(vendedorBean);
				calculaPercVendedor();	
				buscaCPFVendedor = "";
			} else {
				FacesContext context = FacesContext.getCurrentInstance();
		        context.addMessage(null, new FacesMessage("Alerta",  "Vendedor não encontrado"));
			}
		}
	}
	
	public void adicionaCompradorLista() throws ProjetoException {
		if (compradorSelect.getId() == null) {
			FacesMessage msg = null;
			msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Digite um Comprador válido!", "");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		} else if (this.loteBean.getValorLance() != null && this.loteBean.getValorLance() !=0 
				&& this.loteBean.getNumeroParcela() != null &&  this.loteBean.getNumeroParcela() != 0
				&&  this.loteBean.getDataCompra() != null) {	
			compradorSelect = vendedorService.findOne(compradorSelect.getId());
			CompradoresWrapper compradoresWrapper = new CompradoresWrapper();
			compradoresWrapper.setComprador(compradorSelect);
			
			compradoresWrapper.setValorLance(this.loteBean.getValorLance() / (this.listaDeCompradoresLote.size() + 1));
			compradoresWrapper.setNumeroParcelas(this.loteBean.getNumeroParcela());
			compradoresWrapper.setValorDesconto(this.loteBean.getValorDesconto());
			
			List<ParcelasComprador> listaDeParcelasComprador = new ArrayList<ParcelasComprador>();
			Double parcela = (this.loteBean.getValorLance() - (this.loteBean.getValorDesconto() != null ? this.loteBean.getValorDesconto() : 0d)) ;
			
			Integer numeroParcelas = this.loteBean.getNumeroParcela();

			Utilites utilites = new Utilites();
			
				boolean agrupou = false;
				for (int i = 1; i <= numeroParcelas; i++) {
					for (int z = 0; z < homeLeilao.getAgruparParcelas().size(); z++) {	
						
						if (homeLeilao.getAgruparParcelas().get(z).getAgruparParcela() == i) {
							ParcelasComprador parcelasComprador = new ParcelasComprador();
							parcelasComprador.setNumeroDaParcela(i);
							parcelasComprador.setValorDaParcela(parcela * 2);
							parcelasComprador.setComprador(compradoresWrapper.getComprador());
							parcelasComprador.setDataVencimento(utilites.addMonthDate(loteBean.getDataCompra(), i));
							listaDeParcelasComprador.add(parcelasComprador);
							numeroParcelas --;
							agrupou = true;
						}
					}
					
					// else {
						if (!agrupou){
						ParcelasComprador parcelasComprador = new ParcelasComprador();
						parcelasComprador.setNumeroDaParcela(i);
						parcelasComprador.setValorDaParcela(parcela);
						parcelasComprador.setComprador(compradoresWrapper.getComprador());
						parcelasComprador.setDataVencimento(utilites.addMonthDate(loteBean.getDataCompra(), i));
						listaDeParcelasComprador.add(parcelasComprador);
						}
						agrupou = false;
					//}
				//}//fim loop parcelas agrupadas
				//break;
			}
			compradoresWrapper.setListParcelasComprador(listaDeParcelasComprador);
			this.getListaDeCompradoresLote().add(compradoresWrapper);
			
			//REFAZER PARCELAS SE HOUVER MAIS DE 1 COMPRADOR
			Double parcelaComprador = (this.loteBean.getValorLance() - (this.loteBean.getValorDesconto() != null ? this.loteBean.getValorDesconto() : 0d)) ;
			if (this.getListaDeCompradoresLote().size() > 1) {
				for (int i = 0; i < this.listaDeCompradoresLote.size(); i++) {
					for (int j = 0; j < this.listaDeCompradoresLote.get(i).getListParcelasComprador().size(); j++) {
						parcela = this.listaDeCompradoresLote.get(i).getListParcelasComprador().get(j).getValorDaParcela();
						this.listaDeCompradoresLote.get(i).getListParcelasComprador().get(j).setValorDaParcela(parcela/2);
						
					}
				}
				
			}

			// REFAZER LISTA DE COMPRADORES
			Integer tamListCompradores = this.listaDeCompradoresLote.size();
			Double novoLance = this.loteBean.getValorLance() / tamListCompradores;
			if (this.listaDeCompradoresLote.size() > 1) {
				for (int i = 0; i < this.listaDeCompradoresLote.size(); i++) {
					if (this.listaDeCompradoresLote.get(i).getEditavel() == false) {
						this.listaDeCompradoresLote.get(i).setValorLance(novoLance);
						this.listaDeCompradoresLote.get(i).setNumeroParcelas(this.loteBean.getNumeroParcela());
						this.listaDeCompradoresLote.get(i).setValorDesconto(0d);
					}
				}
			}
			
			calculaPercComprador();	
			compradorSelect = new VendedorBean();
		} else {
			FacesContext context = FacesContext.getCurrentInstance();
	        context.addMessage(null, new FacesMessage("Alerta",  "Verifique se os campos (data da compra, valor do lance, nº de parcelas) estão preenchidos!"));
		}
	}
	
	public void editListaParcelas(){
		double novoValorParcela = (this.loteBean.getValorLance() - this.valorDaParcelaDialog) / (this.loteBean.getNumeroParcela() - 1);
		
		for (int i = 0; i < this.listaDeCompradoresLote.size(); i++) {
			for (int j = 0; j < this.listaDeCompradoresLote.get(i).getListParcelasComprador().size(); j++) {
					if (j != (this.numeroParcela - 1)) {
						this.listaDeCompradoresLote.get(i).getListParcelasComprador().remove(j);
						ParcelasComprador parcelasComprador = new ParcelasComprador();
						parcelasComprador.setComprador(this.listaDeCompradoresLote.get(i).getComprador());
						parcelasComprador.setNumeroDaParcela(j);
						parcelasComprador.setValorDaParcela(novoValorParcela);
						this.listaDeCompradoresLote.get(i).getListParcelasComprador().add(parcelasComprador);
					} else {
						this.listaDeCompradoresLote.get(i).getListParcelasComprador().remove(j);
						ParcelasComprador parcelasComprador = new ParcelasComprador();
						parcelasComprador.setComprador(this.listaDeCompradoresLote.get(i).getComprador());
						parcelasComprador.setNumeroDaParcela(j);
						parcelasComprador.setValorDaParcela(this.valorDaParcelaDialog);
						this.listaDeCompradoresLote.get(i).getListParcelasComprador().add(parcelasComprador);
						
					}
			}
		}
		
		
	}
	
	public void refazerCalculo() {
		Integer tamListCompradores = 0;
		Double valorGeralIndividual = 0.0d;
		for (int i = 0; i < this.listaDeCompradoresLote.size(); i++) {
			if (this.listaDeCompradoresLote.get(i).getEditavel() == true) {
				valorGeralIndividual += this.listaDeCompradoresLote.get(i).getValorLance();
			} else {
				tamListCompradores ++;
			}
		}
		Double novoLance = this.loteBean.getValorLance() - valorGeralIndividual;
		Double novoLanceGeral = novoLance / tamListCompradores;
		if (this.listaDeCompradoresLote.size() > 1) {
			for (int i = 0; i < this.listaDeCompradoresLote.size(); i++) {
				if (this.listaDeCompradoresLote.get(i).getEditavel() == false) {
					this.listaDeCompradoresLote.get(i).setValorLance(novoLanceGeral);
					this.listaDeCompradoresLote.get(i).setNumeroParcelas(this.loteBean.getNumeroParcela());
					this.listaDeCompradoresLote.get(i).setValorDesconto(0d);
				}
			}
		}
	}
	
	public void validaNomeAnimal(FacesContext context, UIComponent component,
			Object value) throws ValidatorException {
		System.out.println("entra valida");
		
		if(animalAux.getNome()==null){
			System.out.println("entrou nulo validator");
			
			FacesMessage msg = 
				new FacesMessage("Informe o nome do animal.");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ValidatorException(msg);

		}
		else
			System.out.println("nao entrou nulo validator");

	}
	
	public void adicionaAnimalLista() throws ProjetoException {
		org.primefaces.context.DefaultRequestContext.getCurrentInstance().update(":formLote:grwgeral");
		if (animalSelect.getNome() == null) {
			animalSelect.setNome(animalAux.getNome());
		}	
		if (animalSelect.getNome() == null) {
			FacesMessage msg = null;
			msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Informe o nome do animal!", "");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		} else {
			if (animalSelect.getId() != null) {
				this.animalSelect = animalService.findOne(this.animalSelect.getId());
				if (!this.loteBean.getListaDeAnimais().contains(this.animalSelect) && loteService.verificaSeAnimalJaCadastrado(animalSelect.getId()) == false){
					this.loteBean.getListaDeAnimais().add(this.animalSelect);
				} else {
					FacesContext context = FacesContext.getCurrentInstance();
			        context.addMessage(null, new FacesMessage("Alerta",  "Animal já cadastrado em outro lote!"));
				}
			} else {
				this.loteBean.getListaDeAnimais().add(this.animalSelect);
			}
			setVisaoDadosAnimais(true);
			animalSelect = new Animal();
			animalAux = new Animal();
			org.primefaces.context.DefaultRequestContext.getCurrentInstance().update("formLote:tabLotes:outdadosanimal");
		}
	}
	
	public void calculaPercVendedor(){
		float totalVend = loteBean.getListaDeVendedores().size();
		float i = 1;
		float perc = (100/totalVend);
		DecimalFormat df = new DecimalFormat("0.##");
		String percForm = df.format(perc).replaceAll(",", ".");
		
		double perc_rest = 0;
		for(VendedorBean vend : loteBean.getListaDeVendedores()) {
			
			
			DecimalFormat df2 = new DecimalFormat("0.##");
			String percRestForm = df2.format(perc_rest).replaceAll(",", ".");
			perc_rest = Double.valueOf(percRestForm) +Double.valueOf(percForm);
			
		if (i == totalVend){
			percForm = df2.format(perc + (100-perc_rest)).replaceAll(",", ".");
			
			vend.setPercentual(Double.valueOf(percForm));
			
			
		}
		else
		
			vend.setPercentual(Double.valueOf(percForm));
		i = i +1;
		}
	
	}
	
	public void calculaPercComprador(){
		float totalComp = loteBean.getListaDeCompradores().size();
		float i = 1;
		float perc = (100/totalComp);
		DecimalFormat df = new DecimalFormat("0.##");
		String percForm = df.format(perc).replaceAll(",", ".");
		
		double perc_rest = 0;
		for(VendedorBean comp : loteBean.getListaDeCompradores()) {
			
			
			DecimalFormat df2 = new DecimalFormat("0.##");
			String percRestForm = df2.format(perc_rest).replaceAll(",", ".");
			perc_rest = Double.valueOf(percRestForm) +Double.valueOf(percForm);
			
		if (i == totalComp){
			percForm = df2.format(perc + (100-perc_rest)).replaceAll(",", ".");
			
			comp.setPercentual(Double.valueOf(percForm));
			
			
		}
		else
		
			comp.setPercentual(Double.valueOf(percForm));
		i = i +1;
		}
	
	}
		
	
	public void onItemSelectAnimal(SelectEvent event) throws Exception {
    	System.out.println("selecionou");
    }
	
	public void onItemSelectVendedor(SelectEvent event) throws Exception {
    	
    }
	
	public void onItemSelectComprador(SelectEvent event) throws Exception {
    	
    }

	/**
	 * Remove animal da lista 
	 * @throws ProjetoException
	 */
	public void removerAnimalDaLista() throws ProjetoException {
		this.loteBean.getListaDeAnimais().remove(this.animalSelect);
	}

	public void removerVendedorDaLista() throws ProjetoException {
		this.loteBean.getListaDeVendedores().remove(this.vendedorSelect);
		calculaPercVendedor();
	}
	
	
	//Metodo que altera o animal na lista do lote
	 public void alterarAnimalDaLista() {
	        for(Animal an : loteBean.getListaDeAnimais()) {
	            if(an.getId().equals(animaledit.getId())) {
	              an = animaledit;  
	                /*	{es.setValorComDesconto((estoqueBean.getTabela().getValorvenda() 
	                        - estoqueBean.getDesconto()) * estoqueBean.getQtd());
	                	
		                //es.setCodPreco(precoBean.getCodTabelaPreco());
		                
		                if(estoqueBean.getAngariadorBean().getId() != null) {
		                
		                    es.setCodAngariador(Integer.valueOf(String.valueOf(estoqueBean.getAngariadorBean().getId())));
		                    //es.getAngariadorBean().setNome(angariadorBean.getNome());
		                }
		                
		                if(estoqueBean.getCallcenter().getId() != null) {
		                    es.setCodCallCenter(Integer.valueOf(String.valueOf(estoqueBean.getCallcenter().getId())));
		                    //es.getCallcenter().setNome(callCenter.getNome());
		                }
		
		                if(estoqueBean.getIndicador().getId() != null) {
		                    es.setCodIndicador(Integer.valueOf(String.valueOf(estoqueBean.getIndicador().getId())));
		                    //es.getIndicador().setNome(indicadorBean.getNome());
		                }
		
		                if (estoqueBean.getAvaliador().getId() != null) {
		                    es.setCodAvaliador(Integer.valueOf(String.valueOf(estoqueBean.getAvaliador().getId())));
		                    //es.getAvaliador().setNome(avaliadorBean.getNome());
		                }
		                
		                estoqueBean = new Estoque();
		                angariadorBean = new FuncionarioBean();
		                avaliadorBean = new FuncionarioBean();
		                callCenter = new FuncionarioBean();
		                indicadorBean = new FuncionarioBean();
		                precoBean = new PrecoBean();
		
		                totalVenda = totalizaVenda();
		                */
	                	}
		                RequestContext.getCurrentInstance().execute("PF('dlgNovoAnimal').hide();");
	                }
	            }
	        	
	
	 public Date incrementaDatasParcelaCompra(Date data) throws ParseException{
		 
		 Calendar calendar = Calendar.getInstance();
			calendar.setTime(data);
			 Format format = new SimpleDateFormat("dd/MM/yyyy");
			    
			int dia = calendar.get(Calendar.DAY_OF_MONTH);
			int mes = calendar.get(Calendar.MONTH) +1;
			int ano = calendar.get(Calendar.YEAR);
			
			if (mes==12)
				ano = ano+1;
			mes = mes +1;
			if (mes == 13) 
					mes = 1;
			
			DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			Date date = (Date)formatter.parse(String.valueOf(dia)+"/"+String.valueOf(mes)+"/"+ String.valueOf(ano));
			return date;
	 }
	public Lotes getLoteBean() {
		return loteBean;
	}

	public void setLoteBean(Lotes loteBean) {
		this.loteBean = loteBean;
	}

	public Animal getAnimalSelect() {
		return animalSelect;
	}

	public void setAnimalSelect(Animal animalSelect) {
		this.animalSelect = animalSelect;
	}

	public Boolean getVisaoDadosAnimais() {
		return visaoDadosAnimais;
	}

	public void setVisaoDadosAnimais(Boolean visaoDadosAnimais) {
		this.visaoDadosAnimais = visaoDadosAnimais;
	}

	public Boolean getVisaoDosDadosDoLote() {
		return visaoDosDadosDoLote;
	}

	public void setVisaoDosDadosDoLote(Boolean visaoDosDadosDoLote) {
		this.visaoDosDadosDoLote = visaoDosDadosDoLote;
	}

	public Boolean getVisaoDoHistoricoDeLotes() {
		return visaoDoHistoricoDeLotes;
	}

	public void setVisaoDoHistoricoDeLotes(Boolean visaoDoHistoricoDeLotes) {
		this.visaoDoHistoricoDeLotes = visaoDoHistoricoDeLotes;
	}


	/**
	 * @return the animaledit
	 */
	public Animal getAnimaledit() {
		return animaledit;
	}


	/**
	 * @param animaledit the animaledit to set
	 */
	public void setAnimaledit(Animal animaledit) {
		this.animaledit = animaledit;
	}


	public LoteService getLoteService() {
		return loteService;
	}


	public void setLoteService(LoteService loteService) {
		this.loteService = loteService;
	}


	public VendedorBean getVendedorSelect() {
		return vendedorSelect;
	}


	public void setVendedorSelect(VendedorBean vendedorSelect) {
		this.vendedorSelect = vendedorSelect;
	}


	public AnimalService getAnimalService() {
		return animalService;
	}


	public void setAnimalService(AnimalService animalService) {
		this.animalService = animalService;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	public VendedorBean getCompradorSelect() {
		return compradorSelect;
	}


	public void setCompradorSelect(VendedorBean compradorSelect) {
		this.compradorSelect = compradorSelect;
	}


	public String getIdMaxLote() {
		return idMaxLote;
	}


	public void setIdMaxLote(String idMaxLote) {
		this.idMaxLote = idMaxLote;
	}

	public List<CompradoresWrapper> getListaDeCompradoresLote() {
		return listaDeCompradoresLote;
	}

	public void setListaDeCompradoresLote(List<CompradoresWrapper> listaDeCompradoresLote) {
		this.listaDeCompradoresLote = listaDeCompradoresLote;
	}

	public Double getValorTotalLances() {
		return valorTotalLances;
	}

	public void setValorTotalLances(Double valorTotalLances) {
		this.valorTotalLances = valorTotalLances;
	}

	public Double getValorDaParcelaDialog() {
		return valorDaParcelaDialog;
	}

	public void setValorDaParcelaDialog(Double valorDaParcelaDialog) {
		this.valorDaParcelaDialog = valorDaParcelaDialog;
	}

	public Integer getNumeroParcela() {
		return numeroParcela;
	}

	public void setNumeroParcela(Integer numeroParcela) {
		this.numeroParcela = numeroParcela;
	}

	public List<ParcelasComprador> getListaParcelasCompradorDeleteObj() {
		return listaParcelasCompradorDeleteObj;
	}

	public void setListaParcelasCompradorDeleteObj(List<ParcelasComprador> listaParcelasCompradorDeleteObj) {
		this.listaParcelasCompradorDeleteObj = listaParcelasCompradorDeleteObj;
	}

	public String getBuscaCPFVendedor() {
		return buscaCPFVendedor;
	}

	public void setBuscaCPFVendedor(String buscaCPFVendedor) {
		this.buscaCPFVendedor = buscaCPFVendedor;
	}

	public VendedorBean getNovoVendedor() {
		return novoVendedor;
	}

	public void setNovoVendedor(VendedorBean novoVendedor) {
		this.novoVendedor = novoVendedor;
	}
	
}
