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
package fr.univlorraine.ecandidat.vaadin.form.combo;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.vaadin.data.util.BeanItemContainer;

import fr.univlorraine.ecandidat.entities.ecandidat.Mail;
import fr.univlorraine.ecandidat.entities.ecandidat.TypeAvis;
import fr.univlorraine.ecandidat.vaadin.form.RequiredComboBox;

/**
 * ComboBox pour les Mail
 *
 * @author Kevin Hergalant
 */
@SuppressWarnings("serial")
public class ComboBoxMail extends RequiredComboBox<Mail> {

	private BeanItemContainer<Mail> container = new BeanItemContainer<>(Mail.class, null);

	private List<Mail> listeMail = new ArrayList<>();

	public ComboBoxMail(final List<Mail> listeMail) {
		super(true);
		setContainerDataSource(container);
		setListMail(listeMail);
	}

	public ComboBoxMail() {
		super(true);
		setContainerDataSource(container);
	}

	public void setListMail(final List<Mail> listeMail) {
		this.listeMail = listeMail;
		container.removeAllItems();
		container.addAll(listeMail);
	}

	/**
	 * Filtre le container
	 *
	 * @param typeAvis
	 */
	public void filterListValue(final TypeAvis typeAvis) {
		container.removeAllItems();
		List<Mail> newList = listeMail.stream().filter(e -> e.getTypeAvis().equals(typeAvis)).collect(Collectors.toList());
		container.addAll(newList);
		if (newList.size() > 0) {
			setValue(newList.get(0));
		}
	}

	public Boolean hasMailDisplay() {
		if (container == null || container.getItemIds() == null) {
			return false;
		}
		return container.getItemIds().size() > 0;
	}
}
