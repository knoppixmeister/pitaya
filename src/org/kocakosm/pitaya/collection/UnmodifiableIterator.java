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

package org.kocakosm.pitaya.collection;

import java.util.Iterator;

/**
 * Unmodifiable {@code Iterator}. Its {@link Iterator#remove()} method always
 * throws an {@link UnsupportedOperationException}).
 *
 * @param <E> the type of the {@code Iterator}'s elements.
 *
 * @author Osman KOCAK
 */
final class UnmodifiableIterator<E> implements Iterator<E>
{
	private final Iterator<E> inner;

	/**
	 * Creates a new {@code UnmodifiableIterator}.
	 *
	 * @param inner the inner iterator.
	 */
	UnmodifiableIterator(Iterator<E> inner)
	{
		this.inner = inner;
	}

	@Override
	public boolean hasNext()
	{
		return inner.hasNext();
	}

	@Override
	public E next()
	{
		return inner.next();
	}

	@Override
	public void remove()
	{
		throw new UnsupportedOperationException();
	}
}
