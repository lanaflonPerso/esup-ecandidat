/**
 *
 */
package fr.univlorraine.ecandidat.vaadin.components;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import com.vaadin.server.StreamResource;

/**
 * @author Kevin
 */
@SuppressWarnings("serial")
public class TagImageSource implements StreamResource.StreamSource {

	ByteArrayOutputStream imagebuffer = null;
	String htmlColor;

	public TagImageSource(final String color) {
		super();
		this.htmlColor = color;
	}

	/*
	 * We need to implement this method that returns
	 * the resource as a stream.
	 */
	@Override
	public InputStream getStream() {
		ByteArrayOutputStream imagebuffer = null;
		if (htmlColor == null) {
			return null;
		}
		/* Create an image and draw something on it. */
		BufferedImage image = new BufferedImage(17, 17,
				BufferedImage.TYPE_INT_RGB);
		Graphics drawable = image.getGraphics();
		drawable.setColor(Color.black);
		drawable.fillRect(0, 0, 17, 17);
		drawable.setColor(Color.decode(htmlColor));
		drawable.fillRect(1, 1, 15, 15);

		try {
			/* Write the image to a buffer. */
			imagebuffer = new ByteArrayOutputStream();
			ImageIO.write(image, "png", imagebuffer);

			/* Return a stream from the buffer. */
			return new ByteArrayInputStream(
					imagebuffer.toByteArray());
		} catch (IOException e) {
			return null;
		}
	}
}
