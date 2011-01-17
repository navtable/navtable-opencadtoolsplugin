/*
 * Copyright (c) 2010. Cartolab (Universidade da Coruña)
 *
 * This file is part of NavTable
 *
 * NavTable is based on the forms application of GisEIEL <http://giseiel.forge.osor.eu/>
 * devoloped by Laboratorio de Bases de Datos (Universidade da Coruña)
 *
 * NavTable is free software: you can redistribute it and/or modify it under the terms
 * of the GNU General Public License as published by the Free Software Foundation, either
 * version 3 of the License, or any later version.
 *
 * NavTable is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with NavTable.
 * If not, see <http://www.gnu.org/licenses/>.
 */

package es.udc.cartolab.gvsig.navtable;

import com.iver.andami.plugins.Extension;

/**
 * This extension does nothing. It's only used to change other extensions' button status (pushed/released)
 *
 * @author Javier Estevez
 *
 */
public class EmptyExtension extends Extension {

	public void initialize() {


	}

	public void execute(String actionCommand) {

	}

	public boolean isEnabled() {
		return true;
	}

	public boolean isVisible() {
		return false;
	}

}
