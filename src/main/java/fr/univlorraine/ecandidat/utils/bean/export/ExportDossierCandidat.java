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
package fr.univlorraine.ecandidat.utils.bean.export;

import java.io.Serializable;

import fr.univlorraine.ecandidat.entities.ecandidat.Candidat;
import fr.univlorraine.ecandidat.entities.ecandidat.CompteMinima;
import fr.univlorraine.ecandidat.utils.MethodUtils;
import lombok.Data;

/**
 * Objet contenant les infos d'un candidat pour l'export
 * 
 * @author Kevin Hergalant
 */
@Data
@SuppressWarnings("serial")
public class ExportDossierCandidat implements Serializable {

	private String numeroDossier;
	private String civilite;
	private String nomPatronymique;
	private String nomUsage;
	private String prenom;
	private String dateNaissance;
	private String villeNaissance;
	private String nationalite;
	private String codeEtudiant;
	private String telPort;
	private String telFixe;
	private String mail;
	private String adresse;
	private String ine;

	public ExportDossierCandidat() {
		super();
	}

	public ExportDossierCandidat(final CompteMinima cptMin, final Candidat candidat, final String dtNaiss, final String adresse, final String ine, final String cleIne) {
		this.setNumeroDossier(MethodUtils.formatToExport(cptMin.getNumDossierOpiCptMin()));
		this.setCivilite(MethodUtils.formatToExport(candidat.getCivilite().getLibCiv()));
		this.setNomPatronymique(MethodUtils.formatToExport(candidat.getNomPatCandidat()));
		this.setNomUsage(MethodUtils.formatToExport(candidat.getNomUsuCandidat()));
		this.setPrenom(MethodUtils.formatToExport(candidat.getPrenomCandidat()));
		this.setVilleNaissance(MethodUtils.formatToExport(candidat.getLibVilleNaissCandidat()));
		this.setNationalite(MethodUtils.formatToExport(candidat.getSiScolPaysNat().getLibNat()));
		this.setCodeEtudiant(MethodUtils.formatToExport(cptMin.getSupannEtuIdCptMin()));
		this.setTelPort(MethodUtils.formatToExport(candidat.getTelPortCandidat()));
		this.setTelFixe(MethodUtils.formatToExport(candidat.getTelCandidat()));
		this.setMail(MethodUtils.formatToExport(cptMin.getMailPersoCptMin()));
		this.setDateNaissance(MethodUtils.formatToExport(dtNaiss));
		this.setAdresse(MethodUtils.formatToExport(adresse));
		if (ine != null && cleIne != null && !ine.equals("") && !ine.equals("")) {
			this.setIne(ine + cleIne);
		} else {
			this.setIne("");
		}
	}
}
