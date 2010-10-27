/*
 * Copyright (c) 2010. Cartolab (Universidade da Coruña)
 *
 * This file is part of NavTable OpenCADTools plug-in
 *
 * This program is free software: you can redistribute it and/or modify it under the terms
 * of the GNU General Public License as published by the Free Software Foundation, either
 * version 3 of the License, or any later version.
 *
 * extEIELForms is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with extEIELForms.
 * If not, see <http://www.gnu.org/licenses/>.
 */

package es.udc.cartolab.gvsig.navtable;

import java.net.URL;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import com.iver.andami.PluginServices;
import com.iver.andami.messages.NotificationManager;
import com.iver.andami.plugins.Extension;
import com.iver.andami.ui.mdiFrame.JToolBarButton;
import com.iver.cit.gvsig.fmap.layers.FLayer;
import com.iver.cit.gvsig.fmap.layers.FLyrVect;
import com.iver.cit.gvsig.listeners.CADListenerManager;
import com.iver.cit.gvsig.listeners.EndGeometryListener;
import com.iver.utiles.XMLEntity;

/**
 * This extension enables or disables automatic NavTable execution after creating a new
 * geometry on a layer. It is compatible only with openCADTools.
 *
 * @author Javier Estevez
 *
 */
public class AutoNavTableExtension extends Extension {

	private static final String LAUNCH_NAVTABLE_ON_CREATE_GEOMETRY_KEY_NAME = "AutoLaunchOnCreateGeom";
	public static final String KEY_NAME = "es.udc.cartolab.gvsig.navtable";

	private boolean formsEnabled = false;
	private final URL offIcon = this.getClass().getClassLoader().getResource("images/forms.png");
	private final URL onIcon = this.getClass().getClassLoader().getResource("images/forms-active.png");

	@Override
	public void initialize() {

		formsEnabled = getPreferences();

		if (formsEnabled) {
			NTEndGeometryListener listener = new NTEndGeometryListener();
			CADListenerManager.addEndGeometryListener(KEY_NAME, listener);
		}

		registerIcons();

	}

	private void registerIcons() {

		PluginServices.getIconTheme().registerDefault(
				"auto-navtable",
				onIcon
			);

	}

	@Override
	public void execute(String actionCommand) {

		//TODO set conf properties, internationalization and localization.
		if (!formsEnabled) {
			NTEndGeometryListener listener = new NTEndGeometryListener();
			CADListenerManager.addEndGeometryListener(KEY_NAME, listener);
			NotificationManager.addInfo("NavTable automático activado");
			formsEnabled = true;
			setIcon(onIcon, "Desactivar NavTable automático");
		} else {
			CADListenerManager.removeEndGeometryListener(KEY_NAME);
			NotificationManager.addInfo("NavTable automático desactivado");
			formsEnabled = false;
			setIcon(offIcon, "Activar NavTable automático");
		}

		savePreferences(formsEnabled);

	}

	public static boolean getPreferences() {
		PluginServices ps = PluginServices.getPluginServices(KEY_NAME);
		XMLEntity xml = ps.getPersistentXML();
		if (xml.contains(LAUNCH_NAVTABLE_ON_CREATE_GEOMETRY_KEY_NAME)) {
			return xml.getBooleanProperty(LAUNCH_NAVTABLE_ON_CREATE_GEOMETRY_KEY_NAME);
		}
		return false;
	}

	public static void savePreferences(boolean value) {
		PluginServices ps = PluginServices.getPluginServices(KEY_NAME);
		XMLEntity xml = ps.getPersistentXML();
		xml.putProperty(LAUNCH_NAVTABLE_ON_CREATE_GEOMETRY_KEY_NAME, value);
	}

	private void setIcon(URL iconURL, String tooltip) {
		JToolBarButton throwNTButton = (JToolBarButton) PluginServices.getMainFrame().getComponentByName("auto-navtable");
		if (throwNTButton!=null) {
			Icon icon = new ImageIcon(iconURL);
			throwNTButton.setIcon(icon);
			throwNTButton.setToolTip(tooltip);
		}
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public boolean isVisible() {
		return true;
	}

	private class NTEndGeometryListener implements EndGeometryListener {

		@Override
		public void endGeometry(FLayer layer) {
			if (layer instanceof FLyrVect) {
				FLyrVect l = (FLyrVect) layer;
				l.setActive(true);
				NavTable nt = new NavTable(l);
				if (nt.init()) {
					PluginServices.getMDIManager().addCentredWindow(nt);
				}
			}
		}

	}

	public void postInitialize() {

			URL icon;
			String tooltip;
			if (formsEnabled) {
				icon = onIcon;
				tooltip = "Desactivar NavTable automático";
			} else {
				icon = offIcon;
				tooltip = "Activar NavTable automático";
			}
			setIcon(icon, tooltip);

	}

}
