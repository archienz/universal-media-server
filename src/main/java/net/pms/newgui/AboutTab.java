/*
 * PS3 Media Server, for streaming any medias to your PS3.
 * Copyright (C) 2008  A.Brochard
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; version 2
 * of the License only.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */
package net.pms.newgui;

import java.awt.Cursor;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JScrollPane;

import net.pms.Messages;
import net.pms.PMS;
import net.pms.util.PropertiesUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

public class AboutTab {
	private static final Logger LOGGER = LoggerFactory.getLogger(AboutTab.class);

	private ImagePanel imagePanel;

	public ImagePanel getImagePanel() {
		return imagePanel;
	}

	public JComponent build() {
		FormLayout layout = new FormLayout(
			"0:grow, pref, 0:grow",
			"pref, 3dlu, pref, 3dlu, pref, 12dlu, pref, 3dlu, pref, 3dlu, pref, 3dlu, pref, 3dlu, pref, 3dlu, pref, 3dlu, p, 3dlu, p");

		PanelBuilder builder = new PanelBuilder(layout);
		builder.setDefaultDialogBorder();
		builder.setOpaque(true);
		CellConstraints cc = new CellConstraints();

		String projectName = PropertiesUtil.getProjectProperties().get("project.name");

		final LinkMouseListener pms3Link = new LinkMouseListener(projectName + " " + PMS.getVersion(),
			"http://www.universalmediaserver.com/");
		JLabel lPms3Link = builder.addLabel(pms3Link.getLabel(), cc.xy(2, 1, "center, fill"));
		lPms3Link.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lPms3Link.addMouseListener(pms3Link);

		// Create a build name from the available git properties
		String commitId = PropertiesUtil.getProjectProperties().get("git.commit.id");
		String commitTime = PropertiesUtil.getProjectProperties().get("git.commit.time");
		String shortCommitId = commitId.substring(0,  9);
		String commitUrl = "http://code.google.com/p/universal-media-server/source/detail?r=" + commitId;
		String buildLabel = Messages.getString("LinksTab.6") + " " + commitTime;

		final LinkMouseListener commitLink = new LinkMouseListener(buildLabel, commitUrl);
		JLabel lCommitLink = builder.addLabel(commitLink.getLabel(), cc.xy(2, 3, "center, fill"));
		lCommitLink.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lCommitLink.addMouseListener(commitLink);

		imagePanel = buildImagePanel();
		builder.add(imagePanel, cc.xy(2, 5, "center, fill"));


		builder.addLabel(Messages.getString("LinksTab.5"), cc.xy(2, 7, "center, fill"));

		final LinkMouseListener ps3msLink = new LinkMouseListener("PS3 Media Server",
			"http://www.ps3mediaserver.org/");
		JLabel lPs3msLink = builder.addLabel(ps3msLink.getLabel(), cc.xy(2, 9, "center, fill"));
		lPs3msLink.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lPs3msLink.addMouseListener(ps3msLink);

		final LinkMouseListener ffmpegLink = new LinkMouseListener("FFmpeg",
			"http://ffmpeg.mplayerhq.hu");
		JLabel lFfmpegLink = builder.addLabel(ffmpegLink.getLabel(), cc.xy(2, 11, "center, fill"));
		lFfmpegLink.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lFfmpegLink.addMouseListener(ffmpegLink);

		final LinkMouseListener mplayerLink = new LinkMouseListener("MPlayer",
			"http://www.mplayerhq.hu");
		JLabel lMplayerLink = builder.addLabel(mplayerLink.getLabel(), cc.xy(2, 13, "center, fill"));
		lMplayerLink.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lMplayerLink.addMouseListener(mplayerLink);

		final LinkMouseListener mplayerSubJunkBuildsLink = new LinkMouseListener("SubJunk's MPlayer builds and InterFrame",
			"http://www.spirton.com/mplayer-mencoder-subjunk-build/");
		JLabel lMplayerSubJunkBuildsLink = builder.addLabel(mplayerSubJunkBuildsLink.getLabel(), cc.xy(2, 15, "center, fill"));
		lMplayerSubJunkBuildsLink.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lMplayerSubJunkBuildsLink.addMouseListener(mplayerSubJunkBuildsLink);

		final LinkMouseListener imageMagickLink = new LinkMouseListener("ImageMagick",
			"http://www.imagemagick.org");
		JLabel lImageMagickLink = builder.addLabel(imageMagickLink.getLabel(), cc.xy(2, 17, "center, fill"));
		lImageMagickLink.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lImageMagickLink.addMouseListener(imageMagickLink);

		final LinkMouseListener mediaInfoLink = new LinkMouseListener("MediaInfo",
			"http://mediainfo.sourceforge.net/en");
		JLabel lMediaInfoLink = builder.addLabel(mediaInfoLink.getLabel(), cc.xy(2, 19, "center, fill"));
		lMediaInfoLink.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lMediaInfoLink.addMouseListener(mediaInfoLink);

		final LinkMouseListener avisynthMTLink = new LinkMouseListener("AviSynth MT",
			"http://forum.doom9.org/showthread.php?t=148782");
		JLabel lAvisynthMTLink = builder.addLabel(avisynthMTLink.getLabel(), cc.xy(2, 21, "center, fill"));
		lAvisynthMTLink.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lAvisynthMTLink.addMouseListener(avisynthMTLink);

		JScrollPane scrollPane = new JScrollPane(builder.getPanel());
		scrollPane.setBorder(BorderFactory.createEmptyBorder());
		return scrollPane;
	}

	private static class LinkMouseListener implements MouseListener {
		private final String name;
		private final String link;

		public LinkMouseListener(String n, String l) {
			name = n;
			link = l;
		}

		public String getLabel() {
			final StringBuilder sb = new StringBuilder();
			sb.append("<html>");
			sb.append("<a href=\"");
			sb.append(link);
			sb.append("\">");
			sb.append(name);
			sb.append("</a>");
			sb.append("</html>");
			return sb.toString();
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			try {
				PMS.get().getRegistry().browseURI(link);
			} catch (Exception e1) {
				LOGGER.debug("Caught exception", e1);
			}
		}

		@Override
		public void mouseEntered(MouseEvent e) {
		}

		@Override
		public void mouseExited(MouseEvent e) {
		}

		@Override
		public void mousePressed(MouseEvent e) {
		}

		@Override
		public void mouseReleased(MouseEvent e) {
		}
	}

	public ImagePanel buildImagePanel() {
		BufferedImage bi = null;
		try {
			bi = ImageIO.read(LooksFrame.class.getResourceAsStream("/resources/images/logo.png"));
		} catch (IOException e) {
			LOGGER.debug("Caught exception", e);
		}
		return new ImagePanel(bi);
	}
}
