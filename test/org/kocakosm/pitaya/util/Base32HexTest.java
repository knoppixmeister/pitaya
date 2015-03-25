/*----------------------------------------------------------------------------*
 * This file is part of Pitaya.                                               *
 * Copyright (C) 2012-2015 Osman KOCAK <kocakosm@gmail.com>                   *
 *                                                                            *
 * This program is free software: you can redistribute it and/or modify it    *
 * under the terms of the GNU Lesser General Public License as published by   *
 * the Free Software Foundation, either version 3 of the License, or (at your *
 * option) any later version.                                                 *
 * This program is distributed in the hope that it will be useful, but        *
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY *
 * or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public     *
 * License for more details.                                                  *
 * You should have received a copy of the GNU Lesser General Public License   *
 * along with this program. If not, see <http://www.gnu.org/licenses/>.       *
 *----------------------------------------------------------------------------*/

package org.kocakosm.pitaya.util;

import static org.junit.Assert.*;

import org.kocakosm.pitaya.charset.ASCII;

import java.util.Random;

import org.junit.Test;

/**
 * Base32Hex unit tests.
 *
 * @author Osman KOCAK
 */
public final class Base32HexTest
{
	private static final Random RND = new Random();

	@Test
	public void testRFC4648TestVectorsWithPadding()
	{
		BaseEncoding e = BaseEncoding.BASE_32_HEX;
		assertEquals("", e.encode(ascii("")));
		assertEquals("CO======", e.encode(ascii("f")));
		assertEquals("CPNG====", e.encode(ascii("fo")));
		assertEquals("CPNMU===", e.encode(ascii("foo")));
		assertEquals("CPNMUOG=", e.encode(ascii("foob")));
		assertEquals("CPNMUOJ1", e.encode(ascii("fooba")));
		assertEquals("CPNMUOJ1E8======", e.encode(ascii("foobar")));

		assertArrayEquals(ascii("foobar"), e.decode("CPNMUOJ1E8======"));
		assertArrayEquals(ascii("fooba"), e.decode("CPNMUOJ1"));
		assertArrayEquals(ascii("foob"), e.decode("CPNMUOG="));
		assertArrayEquals(ascii("foo"), e.decode("CPNMU==="));
		assertArrayEquals(ascii("fo"), e.decode("CPNG===="));
		assertArrayEquals(ascii("f"), e.decode("CO======"));
		assertArrayEquals(ascii(""), e.decode(""));
	}

	@Test
	public void testRFC4648TestVectorsWithoutPadding()
	{
		BaseEncoding e = BaseEncoding.BASE_32_HEX.withoutPadding();
		assertEquals("", e.encode(ascii("")));
		assertEquals("CO", e.encode(ascii("f")));
		assertEquals("CPNG", e.encode(ascii("fo")));
		assertEquals("CPNMU", e.encode(ascii("foo")));
		assertEquals("CPNMUOG", e.encode(ascii("foob")));
		assertEquals("CPNMUOJ1", e.encode(ascii("fooba")));
		assertEquals("CPNMUOJ1E8", e.encode(ascii("foobar")));

		assertArrayEquals(ascii("foobar"), e.decode("CPNMUOJ1E8"));
		assertArrayEquals(ascii("fooba"), e.decode("CPNMUOJ1"));
		assertArrayEquals(ascii("foob"), e.decode("CPNMUOG"));
		assertArrayEquals(ascii("foo"), e.decode("CPNMU"));
		assertArrayEquals(ascii("fo"), e.decode("CPNG"));
		assertArrayEquals(ascii("f"), e.decode("CO"));
		assertArrayEquals(ascii(""), e.decode(""));
	}

	@Test
	public void testEncodeAndDecodeRandomDataWithPadding()
	{
		BaseEncoding e = BaseEncoding.BASE_32_HEX;
		for (int i = 0; i < 100; i++) {
			byte[] bytes = new byte[RND.nextInt(2049)];
			RND.nextBytes(bytes);
			assertArrayEquals(bytes, e.decode(e.encode(bytes)));
		}
	}

	@Test
	public void testEncodeAndDecodeRandomDataWithoutPadding()
	{
		BaseEncoding e = BaseEncoding.BASE_32_HEX.withoutPadding();
		for (int i = 0; i < 100; i++) {
			byte[] bytes = new byte[RND.nextInt(2049)];
			RND.nextBytes(bytes);
			assertArrayEquals(bytes, e.decode(e.encode(bytes)));
		}
	}

	@Test
	public void testDecodeWithWhitespaces()
	{
		BaseEncoding e = BaseEncoding.BASE_32_HEX;
		assertArrayEquals(ascii(""), e.decode(" \t  \r\n"));
		assertArrayEquals(ascii("hello"), e.decode("\nD1\t  IMOR3F\r"));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testDecodeWithInvalidLength()
	{
		BaseEncoding.BASE_32_HEX.decode("abcd");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testDecodeWithInvalidCharacter()
	{
		BaseEncoding.BASE_32_HEX.decode("MAXB8===");
	}

	private byte[] ascii(String str)
	{
		return ASCII.encode(str);
	}
}
