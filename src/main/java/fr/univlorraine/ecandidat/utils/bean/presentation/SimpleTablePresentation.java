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
package fr.univlorraine.ecandidat.utils.bean.presentation;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Objet servant à la présentation basique de donnée
 * @author Kevin Hergalant
 *
 */
@Data
@EqualsAndHashCode(of={"code"})
public class SimpleTablePresentation implements Serializable {
	/**serialVersionUID**/
	private static final long serialVersionUID = -1362401047200086315L;
	
	private Integer order;
	private String code;
	private String title;
	private Object value;
	private String shortValue;
	private LocalDateTime date;
	
	public final static String CHAMPS_ORDER = "order";
	public final static String CHAMPS_CODE = "code";
	public final static String CHAMPS_TITLE = "title";
	public final static String CHAMPS_VALUE = "value";
	public final static String CHAMPS_DATE = "date";
	public final static String CHAMPS_ACTION = "action";
	
	public SimpleTablePresentation(Integer order,String code,String title, Object value) {
		super();
		this.order = order;
		this.code = code;
		this.title = title;
		this.value = value;
	}
	
	public SimpleTablePresentation(String code,String title, Object value) {
		super();
		this.code = code;
		this.title = title;
		this.value = value;
	}

	public SimpleTablePresentation(Integer order,String code, 
			String title, String value,	LocalDateTime date) {
		this(order,code,title,value);
		this.date = date;
	}

	public SimpleTablePresentation(String title, Object value) {
		this.title = title;
		this.value = value;
	}
	
}
