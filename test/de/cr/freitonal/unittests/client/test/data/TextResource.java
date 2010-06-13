package de.cr.freitonal.unittests.client.test.data;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class TextResource {
	private final String filename;

	public TextResource(String filename) {
		this.filename = filename;
	}

	public String getText() {
		File file = new File(filename);
		byte[] buffer = new byte[(int) file.length()];
		BufferedInputStream bufferedInputStream;
		try {
			bufferedInputStream = new BufferedInputStream(new FileInputStream(file));
			bufferedInputStream.read(buffer);
		} catch (FileNotFoundException e) {
			throw new IllegalArgumentException(e);
		} catch (IOException e) {
			throw new IllegalArgumentException(e);
		}

		return new String(buffer);
	}
}
