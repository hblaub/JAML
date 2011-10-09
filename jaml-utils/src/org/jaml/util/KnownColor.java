/*******************************************************************************
 * Copyright (C) 2011 by Harry Blauberg
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 * 
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package org.jaml.util;

import java.awt.Color;

/**
 * Enumeration of all known HTML colors
 */
public enum KnownColor {
	Transparent(255, 255, 255, 0), AliceBlue(240, 248, 255, 255), AntiqueWhite(
			250, 235, 215, 255), Aqua(0, 255, 255, 255), Aquamarine(127, 255,
			212, 255), Azure(240, 255, 255, 255), Beige(245, 245, 220, 255), Bisque(
			255, 228, 196, 255), Black(0, 0, 0, 255), BlanchedAlmond(255, 235,
			205, 255), Blue(0, 0, 255, 255), BlueViolet(138, 43, 226, 255), Brown(
			165, 42, 42, 255), BurlyWood(222, 184, 135, 255), CadetBlue(95,
			158, 160, 255), Chartreuse(127, 255, 0, 255), Chocolate(210, 105,
			30, 255), Coral(255, 127, 80, 255), CornflowerBlue(100, 149, 237,
			255), Cornsilk(255, 248, 220, 255), Crimson(220, 20, 60, 255), Cyan(
			0, 255, 255, 255), DarkBlue(0, 0, 139, 255), DarkCyan(0, 139, 139,
			255), DarkGoldenrod(184, 134, 11, 255), DarkGray(169, 169, 169, 255), DarkGreen(
			0, 100, 0, 255), DarkKhaki(189, 183, 107, 255), DarkMagenta(139, 0,
			139, 255), DarkOliveGreen(85, 107, 47, 255), DarkOrange(255, 140,
			0, 255), DarkOrchid(153, 50, 204, 255), DarkRed(139, 0, 0, 255), DarkSalmon(
			233, 150, 122, 255), DarkSeaGreen(143, 188, 139, 255), DarkSlateBlue(
			72, 61, 139, 255), DarkSlateGray(47, 79, 79, 255), DarkTurquoise(0,
			206, 209, 255), DarkViolet(148, 0, 211, 255), DeepPink(255, 20,
			147, 255), DeepSkyBlue(0, 191, 255, 255), DimGray(105, 105, 105,
			255), DodgerBlue(30, 144, 255, 255), Firebrick(178, 34, 34, 255), FloralWhite(
			255, 250, 240, 255), ForestGreen(34, 139, 34, 255), Fuchsia(255, 0,
			255, 255), Gainsboro(220, 220, 220, 255), GhostWhite(248, 248, 255,
			255), Gold(255, 215, 0, 255), Goldenrod(218, 165, 32, 255), Gray(
			128, 128, 128, 255), Green(0, 128, 0, 255), GreenYellow(173, 255,
			47, 255), Honeydew(240, 255, 240, 255), HotPink(255, 105, 180, 255), IndianRed(
			205, 92, 92, 255), Indigo(75, 0, 130, 255), Ivory(255, 255, 240,
			255), Khaki(240, 230, 140, 255), Lavender(230, 230, 250, 255), LavenderBlush(
			255, 240, 245, 255), LawnGreen(124, 252, 0, 255), LemonChiffon(255,
			250, 205, 255), LightBlue(173, 216, 230, 255), LightCoral(240, 128,
			128, 255), LightCyan(224, 255, 255, 255), LightGoldenrodYellow(250,
			250, 210, 255), LightGray(211, 211, 211, 255), LightGreen(144, 238,
			144, 255), LightPink(255, 182, 193, 255), LightSalmon(255, 160,
			122, 255), LightSeaGreen(32, 178, 170, 255), LightSkyBlue(135, 206,
			250, 255), LightSlateGray(119, 136, 153, 255), LightSteelBlue(176,
			196, 222, 255), LightYellow(255, 255, 224, 255), Lime(0, 255, 0,
			255), LimeGreen(50, 205, 50, 255), Linen(250, 240, 230, 255), Magenta(
			255, 0, 255, 255), Maroon(128, 0, 0, 255), MediumAquamarine(102,
			205, 170, 255), MediumBlue(0, 0, 205, 255), MediumOrchid(186, 85,
			211, 255), MediumPurple(147, 112, 219, 255), MediumSeaGreen(60,
			179, 113, 255), MediumSlateBlue(123, 104, 238, 255), MediumSpringGreen(
			0, 250, 154, 255), MediumTurquoise(72, 209, 204, 255), MediumVioletRed(
			199, 21, 133, 255), MidnightBlue(25, 25, 112, 255), MintCream(245,
			255, 250, 255), MistyRose(255, 228, 225, 255), Moccasin(255, 228,
			181, 255), NavajoWhite(255, 222, 173, 255), Navy(0, 0, 128, 255), OldLace(
			253, 245, 230, 255), Olive(128, 128, 0, 255), OliveDrab(107, 142,
			35, 255), Orange(255, 165, 0, 255), OrangeRed(255, 69, 0, 255), Orchid(
			218, 112, 214, 255), PaleGoldenrod(238, 232, 170, 255), PaleGreen(
			152, 251, 152, 255), PaleTurquoise(175, 238, 238, 255), PaleVioletRed(
			219, 112, 147, 255), PapayaWhip(255, 239, 213, 255), PeachPuff(255,
			218, 185, 255), Peru(205, 133, 63, 255), Pink(255, 192, 203, 255), Plum(
			221, 160, 221, 255), PowderBlue(176, 224, 230, 255), Purple(128, 0,
			128, 255), Red(255, 0, 0, 255), RosyBrown(188, 143, 143, 255), RoyalBlue(
			65, 105, 225, 255), SaddleBrown(139, 69, 19, 255), Salmon(250, 128,
			114, 255), SandyBrown(244, 164, 96, 255), SeaGreen(46, 139, 87, 255), SeaShell(
			255, 245, 238, 255), Sienna(160, 82, 45, 255), Silver(192, 192,
			192, 255), SkyBlue(135, 206, 235, 255), SlateBlue(106, 90, 205, 255), SlateGray(
			112, 128, 144, 255), Snow(255, 250, 250, 255), SpringGreen(0, 255,
			127, 255), SteelBlue(70, 130, 180, 255), Tan(210, 180, 140, 255), Teal(
			0, 128, 128, 255), Thistle(216, 191, 216, 255), Tomato(255, 99, 71,
			255), Turquoise(64, 224, 208, 255), Violet(238, 130, 238, 255), Wheat(
			245, 222, 179, 255), White(255, 255, 255, 255), WhiteSmoke(245,
			245, 245, 255), Yellow(255, 255, 0, 255), YellowGreen(154, 205, 50,
			255);

	private Color color;

	private KnownColor(int red, int green, int blue, int alpha) {
		this.color = new Color(red, green, blue, alpha);
	}

	public Color getColor() {
		return color;
	}
}
