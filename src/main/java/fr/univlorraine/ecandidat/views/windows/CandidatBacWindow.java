/**
 *  ESUP-Portail eCandidat - Copyright (c) 2016 ESUP-Portail consortium
 *
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package fr.univlorraine.ecandidat.views.windows;

import java.io.Serializable;
import java.time.LocalDate;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.ApplicationContext;

import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Field;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import fr.univlorraine.ecandidat.StyleConstants;
import fr.univlorraine.ecandidat.controllers.CandidatParcoursController;
import fr.univlorraine.ecandidat.controllers.TableRefController;
import fr.univlorraine.ecandidat.entities.ecandidat.CandidatBacOuEqu;
import fr.univlorraine.ecandidat.entities.ecandidat.CandidatBacOuEqu_;
import fr.univlorraine.ecandidat.entities.ecandidat.SiScolBacOuxEqu;
import fr.univlorraine.ecandidat.entities.ecandidat.SiScolEtablissement;
import fr.univlorraine.ecandidat.utils.ConstanteUtils;
import fr.univlorraine.ecandidat.vaadin.components.OneClickButton;
import fr.univlorraine.ecandidat.vaadin.form.CustomBeanFieldGroup;
import fr.univlorraine.ecandidat.vaadin.form.RequiredComboBox;
import fr.univlorraine.ecandidat.vaadin.form.RequiredIntegerField;
import fr.univlorraine.ecandidat.vaadin.form.combo.ComboBoxBacOuEqu;
import fr.univlorraine.ecandidat.vaadin.form.siscol.ComboBoxCommune;
import fr.univlorraine.ecandidat.vaadin.form.siscol.ComboBoxDepartement;
import fr.univlorraine.ecandidat.vaadin.form.siscol.ComboBoxEtablissement;
import fr.univlorraine.ecandidat.vaadin.form.siscol.ComboBoxPays;

/**
 * Fenêtre d'édition du bac
 * @author Kevin Hergalant
 *
 */
@Configurable(preConstruction=true)
public class CandidatBacWindow extends CandidatScolariteWindow {
	
	/** serialVersionUID **/
	private static final long serialVersionUID = 8279285838139858898L;
	
	public static final String[] FIELDS_ORDER = {
		CandidatBacOuEqu_.anneeObtBac.getName(),
		CandidatBacOuEqu_.siScolBacOuxEqu.getName(),				
		CandidatBacOuEqu_.siScolMentionNivBac.getName(),
		CandidatBacOuEqu_.siScolPays.getName(),
		CandidatBacOuEqu_.siScolDepartement.getName(),
		CandidatBacOuEqu_.siScolCommune.getName(),		
		CandidatBacOuEqu_.siScolEtablissement.getName()
	};

	@Resource
	private transient ApplicationContext applicationContext;
	@Resource
	private transient CandidatParcoursController candidatParcoursController;
	@Resource
	private transient TableRefController tableRefController;
	

	/* Composants */
	private CustomBeanFieldGroup<CandidatBacOuEqu> fieldGroup;
	private OneClickButton btnEnregistrer;
	private OneClickButton btnAnnuler;
	private ComboBoxBacOuEqu comboBoxBacOuEqu;
	private RequiredIntegerField fieldAnneeObt;
	private BacWindowListener bacWindowListener;
	
	/*Les champs*/
	private ComboBoxPays comboBoxPays;
	/*Champs departement*/
	private ComboBoxDepartement comboBoxDepartement;
	/*Champs commune*/
	private ComboBoxCommune comboBoxCommune;
	/*Champs etablissement*/
	private ComboBoxEtablissement comboBoxEtablissement;
	/*Champs mention*/
	private RequiredComboBox<SiScolEtablissement> comboBoxMention;
	
	/**
	 * Crée une fenêtre d'édition de bac
	 * @param bacOuEqu le bac à éditer
	 */
	@SuppressWarnings("unchecked")
	public CandidatBacWindow(CandidatBacOuEqu bacOuEqu, Boolean isEdition) {
		/* Layout */
		VerticalLayout layout = new VerticalLayout();
		layout.setWidth(100, Unit.PERCENTAGE);
		layout.setMargin(true);
		layout.setSpacing(true);
		setContent(layout);

		/* Titre */
		setCaption(applicationContext.getMessage("infobac.window", null, UI.getCurrent().getLocale()));
		
		/*Gestion sans bac*/
		SiScolBacOuxEqu bacNoBac = tableRefController.getBacNoBac();
		
		/*Label explicatif*/
		Label labelExplicatif = new Label(applicationContext.getMessage("infobac.explication.annee", null, UI.getCurrent().getLocale()));
		labelExplicatif.setSizeUndefined();
		labelExplicatif.addStyleName(ValoTheme.LABEL_TINY);
		labelExplicatif.addStyleName(StyleConstants.LABEL_MORE_BOLD);
		labelExplicatif.addStyleName(StyleConstants.LABEL_ITALIC);
		layout.addComponent(labelExplicatif);
		layout.setComponentAlignment(labelExplicatif, Alignment.BOTTOM_CENTER);
		
		/*Layout du bac*/
		HorizontalLayout hlNoBac = new HorizontalLayout();
		hlNoBac.setWidth(100, Unit.PERCENTAGE);
		hlNoBac.setSpacing(true);
		layout.addComponent(hlNoBac);		
		
		
		/*Layout adresse*/		
		fieldGroup = new CustomBeanFieldGroup<CandidatBacOuEqu>(CandidatBacOuEqu.class,ConstanteUtils.TYP_FORM_CANDIDAT);
		fieldGroup.setItemDataSource(bacOuEqu);
		FormLayout formLayout = new FormLayout();
		formLayout.setWidth(100, Unit.PERCENTAGE);
		formLayout.setSpacing(true);
		for (String fieldName : FIELDS_ORDER) {
			Field<?> field = fieldGroup.buildAndBind(applicationContext.getMessage("infobac." + fieldName, null, UI.getCurrent().getLocale()), fieldName);
			field.setWidth(100, Unit.PERCENTAGE);
			formLayout.addComponent(field);
		}
		
				
		layout.addComponent(formLayout);
		layout.setExpandRatio(formLayout, 1);
		
		/* Ajoute les boutons */
		HorizontalLayout buttonsLayout = new HorizontalLayout();
		buttonsLayout.setWidth(100, Unit.PERCENTAGE);
		buttonsLayout.setSpacing(true);
		layout.addComponent(buttonsLayout);

		btnAnnuler = new OneClickButton(applicationContext.getMessage("btnAnnuler", null, UI.getCurrent().getLocale()), FontAwesome.TIMES);
		btnAnnuler.addClickListener(e -> close());
		buttonsLayout.addComponent(btnAnnuler);
		buttonsLayout.setComponentAlignment(btnAnnuler, Alignment.MIDDLE_LEFT);

		btnEnregistrer = new OneClickButton(applicationContext.getMessage("btnSave", null, UI.getCurrent().getLocale()), FontAwesome.SAVE);
		btnEnregistrer.addStyleName(ValoTheme.BUTTON_PRIMARY);		
		btnEnregistrer.addClickListener(e -> {			
			try {			
				/* Valide la saisie de l'adresse*/
				fieldGroup.commit();
				/* Enregistre la formation saisie */
				bacWindowListener.btnOkClick(candidatParcoursController.saveBac(bacOuEqu));
				/* Ferme la fenêtre */
				close();
			} catch (CommitException ce) {			
			}
		});
		buttonsLayout.addComponent(btnEnregistrer);
		buttonsLayout.setComponentAlignment(btnEnregistrer, Alignment.MIDDLE_RIGHT);

		/* Centre la fenêtre */
		center();
		
		/*Champs pays*/
		comboBoxPays = (ComboBoxPays)fieldGroup.getField(CandidatBacOuEqu_.siScolPays.getName());
		changeRequired(comboBoxPays, true);
		/*Champs departement*/
		comboBoxDepartement = (ComboBoxDepartement)fieldGroup.getField(CandidatBacOuEqu_.siScolDepartement.getName());
		/*Champs commune*/
		comboBoxCommune = (ComboBoxCommune)fieldGroup.getField(CandidatBacOuEqu_.siScolCommune.getName());
		/*Champs etablissement*/
		comboBoxEtablissement = (ComboBoxEtablissement)fieldGroup.getField(CandidatBacOuEqu_.siScolEtablissement.getName());
		/*Champs mention*/
		comboBoxMention = (RequiredComboBox<SiScolEtablissement>)fieldGroup.getField(CandidatBacOuEqu_.siScolMentionNivBac.getName());
		/*Champs bac*/
		comboBoxBacOuEqu = (ComboBoxBacOuEqu)fieldGroup.getField(CandidatBacOuEqu_.siScolBacOuxEqu.getName());
		/*Champs annee d'obtention*/
		fieldAnneeObt = (RequiredIntegerField)fieldGroup.getField(CandidatBacOuEqu_.anneeObtBac.getName());
		changeRequired(fieldAnneeObt, true);		
		
		initForm(comboBoxPays, comboBoxDepartement, comboBoxCommune, comboBoxEtablissement, fieldAnneeObt, 
				bacOuEqu.getSiScolPays(), bacOuEqu.getSiScolDepartement(), bacOuEqu.getSiScolCommune(), 
				bacOuEqu.getSiScolEtablissement(), LocalDate.now().getYear());
		
		
		OneClickButton buttonBac = new OneClickButton(applicationContext.getMessage("infobac.bac.bouton", null, UI.getCurrent().getLocale()),FontAwesome.GRADUATION_CAP);
		OneClickButton buttonNoBac = new OneClickButton(applicationContext.getMessage("infobac.nobac.bouton", null, UI.getCurrent().getLocale()),FontAwesome.GRADUATION_CAP);
		/*Gestion sans bac*/
		if (bacNoBac!=null){			
			/*J'ai le bac*/			
			buttonBac.addStyleName(ValoTheme.BUTTON_TINY);			
			buttonBac.addClickListener(e->{					
				buttonBac.setEnabled(false);
				buttonNoBac.setEnabled(true);
				initFormBac(bacNoBac, true);
				formLayout.setVisible(true);
				labelExplicatif.setVisible(true);
				btnEnregistrer.setEnabled(true);
				center();
			});
			hlNoBac.addComponent(buttonBac);
			hlNoBac.setComponentAlignment(buttonBac, Alignment.MIDDLE_CENTER);
			
			/*Je n'ai pas le bac*/
			buttonNoBac.addStyleName(ValoTheme.BUTTON_TINY);
			buttonNoBac.addClickListener(e->{
				buttonBac.setEnabled(true);
				buttonNoBac.setEnabled(false);
				initFormBac(bacNoBac, false);
				formLayout.setVisible(true);
				labelExplicatif.setVisible(false);
				btnEnregistrer.setEnabled(true);
				center();
			});
			hlNoBac.addComponent(buttonNoBac);
			hlNoBac.setComponentAlignment(buttonNoBac, Alignment.MIDDLE_CENTER);
			
		}else{
			hlNoBac.setVisible(false);
		}
		fieldAnneeObt.addValueChangeListener(e->{
			filterListSeries(bacNoBac);
		});
		
		if (isEdition){
			if (bacNoBac!=null){				
				if (bacOuEqu.getSiScolBacOuxEqu().equals(bacNoBac)){
					buttonBac.setEnabled(true);
					buttonNoBac.setEnabled(false);
					initFormBac(bacNoBac, false);
					labelExplicatif.setVisible(false);
				}else{
					buttonBac.setEnabled(false);
					buttonNoBac.setEnabled(true);
					filterListSeries(bacNoBac);
					comboBoxBacOuEqu.setValue(bacOuEqu.getSiScolBacOuxEqu());
					labelExplicatif.setVisible(true);
				}
			}else{
				filterListSeries(bacNoBac);
				comboBoxBacOuEqu.setValue(bacOuEqu.getSiScolBacOuxEqu());
				labelExplicatif.setVisible(true);
			}
		}else if (bacNoBac!=null){
			formLayout.setVisible(false);
			labelExplicatif.setVisible(false);
			btnEnregistrer.setEnabled(false);
		}
	}
	
	/** Initialise le formulaire
	 * @param bacNoBac
	 * @param isWithBac
	 */
	private void initFormBac(SiScolBacOuxEqu bacNoBac, Boolean isWithBac){
		if (isWithBac){			
			changeRequired(comboBoxPays, true);
			changeRequired(comboBoxDepartement, true);
			changeRequired(comboBoxCommune, true);
			changeRequired(comboBoxEtablissement, true);
			changeRequired(fieldAnneeObt, true);
			comboBoxPays.setVisible(true);
			comboBoxDepartement.setVisible(true);
			comboBoxCommune.setVisible(true);
			comboBoxEtablissement.setVisible(true);
			fieldAnneeObt.setVisible(true);
			comboBoxMention.setVisible(true);
			comboBoxBacOuEqu.setCaption(applicationContext.getMessage("infobac.siScolBacOuxEqu", null, UI.getCurrent().getLocale()));
			filterListSeries(bacNoBac);
		}else{
			changeRequired(comboBoxPays, false);
			changeRequired(comboBoxDepartement, false);
			changeRequired(comboBoxCommune, false);
			changeRequired(comboBoxEtablissement, false);
			changeRequired(fieldAnneeObt, false);
			comboBoxPays.setVisible(false);
			comboBoxDepartement.setVisible(false);
			comboBoxCommune.setVisible(false);
			comboBoxEtablissement.setVisible(false);
			fieldAnneeObt.setVisible(false);
			comboBoxMention.setVisible(false);
			comboBoxPays.setValue(null);
			fieldAnneeObt.setValue(null);
			comboBoxDepartement.setValue(null);
			comboBoxCommune.setValue(null);
			comboBoxEtablissement.setValue(null);
			comboBoxMention.setValue(null);
			comboBoxBacOuEqu.setCaption(applicationContext.getMessage("infobac.siScolBacOuxEqu.sansbac", null, UI.getCurrent().getLocale()));
			comboBoxBacOuEqu.filterAndSelectNoBac(bacNoBac);
		}		
	}
	
	/**
	 * Filtre la liste des series
	 */
	private void filterListSeries(SiScolBacOuxEqu bacNoBac){
		SiScolBacOuxEqu valeurSelected = null;
		if (comboBoxBacOuEqu.getValue()!=null){
			valeurSelected = (SiScolBacOuxEqu) comboBoxBacOuEqu.getValue();
		}
		if (fieldAnneeObt.isValid()){
			try{				
				comboBoxBacOuEqu.filterListValue(Integer.valueOf(fieldAnneeObt.getValue()),bacNoBac);				
			}catch(Exception ex){
				
			}	
		}else{
			comboBoxBacOuEqu.filterListValue(null,bacNoBac);
		}
		if (valeurSelected!=null){
			comboBoxBacOuEqu.setValue(valeurSelected);
		}
	}
	
	/** Change l'etat obligatoire d'un champs
	 * @param field
	 * @param isRequired
	 */
	private void changeRequired(Field<?> field, Boolean isRequired){
		field.setRequired(isRequired);
		if (isRequired){
			field.setRequiredError(applicationContext.getMessage("validation.obigatoire", null, UI.getCurrent().getLocale()));
		}else{
			field.setRequiredError(null);
		}
	}
	
	/**
	 * Défini le 'BacWindowListener' utilisé
	 * @param bacWindowListener
	 */
	public void addBacWindowListener(BacWindowListener bacWindowListener) {
		this.bacWindowListener = bacWindowListener;
	}

	/**
	 * Interface pour récupérer un click sur Oui ou Non.
	 */
	public interface BacWindowListener extends Serializable {

		/**
		 * Appelé lorsque Oui est cliqué.
		 * @param candidatBacOuEqu 
		 */
		public void btnOkClick(CandidatBacOuEqu candidatBacOuEqu);

	}
}
