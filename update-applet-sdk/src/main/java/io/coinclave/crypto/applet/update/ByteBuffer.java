/*
 *  ---------
 * |.##> <##.|  Open Smart Card Development Platform (www.openscdp.org)
 * |#       #|  
 * |#       #|  Copyright (c) 1999-2006 CardContact Software & System Consulting
 * |'##> <##'|  Andreas Schwier, 32429 Minden, Germany (www.cardcontact.de)
 *  --------- 
 *
 *  This file is part of OpenSCDP.
 *
 *  OpenSCDP is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License version 2 as
 *  published by the Free Software Foundation.
 *
 *  OpenSCDP is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with OpenSCDP; if not, write to the Free Software
 *  Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 */

package io.coinclave.crypto.applet.update;

import java.util.Arrays;

/**
 * Implements a mutable byte buffer similar to StringBuffer.
 *
 * @author Andreas Schwier (info@cardcontact.de)
 */
public class ByteBuffer {

    // This must match the definitions in the global object
    public static final int HEX = 16;     // to match Number.toHexString(16) method

    public static final int UTF8 = 2;

    public static final int ASCII = 3;

    public static final int BASE64 = 4;

    public static final int CN = 5;

    public static final int OID = 6;

    byte[] buffer = null;

    int length = 0;


    /**
     * Create empty ByteBuffer with initial capacity of 16 bytes
     */
    public ByteBuffer() {
        this(16);
    }


    /**
     * Create empty ByteBuffer with initial capacity as defined by parameter length
     *
     * @param length Initial capacity of ByteBuffer
     */
    public ByteBuffer(int length) {
        buffer = new byte[length];
    }


    /**
     * Create ByteBuffer which contains a exact copy of the byte array passed as parameter
     *
     * @param bytes Byte array to create ByteBuffer from
     */
    public ByteBuffer(byte[] bytes) {
        buffer = Arrays.copyOf(bytes, bytes.length);
        length = bytes.length;
    }

    public ByteBuffer(ByteString byteString) {
        buffer = new byte[byteString.getLength()];
        length = byteString.getLength();
        System.arraycopy(buffer, 0, byteString.getBytes(), 0, length);
    }

    public ByteBuffer(String byteString) throws GPException {
        this(new ByteString(byteString, ByteString.HEX));
    }

    /**
     * Search byte arrays for matching search string and return zero based offset.
     * <p/>
     * Start search at given offset. An empty search string is always found.
     *
     * @param source Byte array to search in
     * @param length Range in source to search in
     * @param search Byte array to search for
     * @param offset Offset to start at
     * @return Zero based offset of match or -1
     */
    public static int find(byte[] source, int length, byte[] search, int offset) {
        while (offset + search.length <= length) {
            int i;
            for (i = 0; (i < search.length) && (source[offset + i] == search[i]); i++) {
            }
            if (i == search.length) {
                return offset;
            }
            offset++;
        }
        return -1;
    }

    /**
     * Search byte arrays for matching search string and return zero based offset.
     * <p/>
     * Start search at given offset. An empty search string is always found.
     *
     * @param source Byte array to search in
     * @param search Byte array to search for
     * @param offset Offset to start at
     * @return Zero based offset of match or -1
     */
    public static int find(byte[] source, byte[] search, int offset) {
        return find(source, source.length, search, offset);
    }

    /**
     * Append single byte
     *
     * @param b
     * @return this
     */
    public ByteBuffer append(byte b) {
        return insert(length, b);
    }

    /**
     * Append byte array
     *
     * @param bytes
     * @return this
     */
    public ByteBuffer append(byte[] bytes) {
        return insert(length, bytes);
    }

    /**
     * Append ByteBuffer
     *
     * @param bb
     * @return this
     */
    public ByteBuffer append(ByteBuffer bb) {
        return insert(length, bb);
    }

    /**
     * Append ByteString
     *
     * @param byteString
     * @return this
     */
    public ByteBuffer append(ByteString byteString) {
        return insert(length, byteString.getBytes());
    }

    /**
     * Ensure that the internal buffer can hold the requested number of bytes
     * <p/>
     * If newCapacity is less than the current capacity, then the meth
     *
     * @param newCapacity
     */
    void ensureCapacity(int newCapacity) {
        if (newCapacity > buffer.length) {
            int newSize = (buffer.length << 1) + 2;
            if (newCapacity > newSize) {
                newSize = newCapacity;
            }

            byte[] newbuffer = new byte[newSize];
            System.arraycopy(buffer, 0, newbuffer, 0, length);
            buffer = newbuffer;
        }
    }

    /**
     * Insert bytes at offset
     *
     * @param offset
     * @param bytes
     * @param length
     * @return
     * @throws IndexOutOfBoundsException
     */
    public ByteBuffer insert(int offset, byte[] bytes, int length) {

        if ((offset > this.length) || (offset < 0)) {
            throw new IndexOutOfBoundsException();
        }

        ensureCapacity(this.length + length);
        System.arraycopy(buffer, offset, buffer, offset + length, this.length - offset);
        System.arraycopy(bytes, 0, buffer, offset, length);
        this.length += length;
        return this;
    }

    /**
     * Insert byte at offset
     *
     * @param offset Position at which to insert the bytes
     * @param b      Byte to insert
     * @return this
     * @throws IndexOutOfBoundsException If offset is not in range
     */
    public ByteBuffer insert(int offset, byte b) {
        return insert(offset, new byte[]{b}, 1);
    }

    /**
     * Insert contents of byte array at offset
     *
     * @param offset Position at which to insert the bytes
     * @param bytes  Byte array to insert
     * @return this
     * @throws IndexOutOfBoundsException If offset is not in range
     */
    public ByteBuffer insert(int offset, byte[] bytes) {
        return insert(offset, bytes, bytes.length);
    }

    /**
     * Insert contents of ByteBuffer at offset
     *
     * @param offset Position at which to insert the ByteBuffer
     * @param bb     ByteBuffer to insert
     * @return this
     * @throws IndexOutOfBoundsException If offset is not in range
     */
    public ByteBuffer insert(int offset, ByteBuffer bb) {
        return insert(offset, bb.buffer, bb.length);
    }

    /**
     * Return length of ByteBuffer
     *
     * @return Length of ByteBuffer
     */
    public int length() {
        return length;
    }

    /**
     * Return byte at zero based offset
     *
     * @param offset
     * @return byte at offset
     * @throws IndexOutOfBoundsException
     */
    public byte getByteAt(int offset) {
        if ((offset < 0) || (offset >= length)) {
            throw new IndexOutOfBoundsException();
        }
        return buffer[offset];
    }

    /**
     * Clear buffer in specified range and move trailing data behind offset + count
     *
     * @param offset
     * @param count
     * @return this
     */
    public ByteBuffer clear(int offset, int count) {
        if ((offset < 0) || (count < 0) || (offset + count > length)) {
            throw new IndexOutOfBoundsException();
        }

        System.arraycopy(buffer, offset + count, buffer, offset, length - (offset + count));
        length -= count;
        return this;
    }

    /**
     * Copy source into buffer at offset
     *
     * @param offset
     * @param source
     * @return this
     */
    public ByteBuffer copy(int offset, byte[] source) {
        if ((offset < 0) || (offset + source.length > length)) {
            throw new IndexOutOfBoundsException();
        }

        System.arraycopy(source, 0, buffer, offset, source.length);
        return this;
    }

    /**
     * Search ByteBuffer for matching search string and return zero based offset.
     * <p/>
     * Start search at given offset. An empty search string is always found.
     *
     * @param search Byte array to search for
     * @param offset Offset to start at
     * @return Zero based offset of match or -1
     */
    public int find(byte[] search, int offset) {
        return find(buffer, length, search, offset);
    }

    /**
     * Return ByteBuffer as byte arrays
     *
     * @return Array of bytes
     */
    public byte[] getBytes() {
        byte[] response = new byte[length];
        System.arraycopy(buffer, 0, response, 0, length);
        return response;
    }

    /**
     * Return part of ByteBuffer as byte arrays
     *
     * @param offset Zero based offset in buffer
     * @param count  Number of bytes to extract
     * @return Array of bytes
     */
    public byte[] getBytes(int offset, int count) {
        byte[] response = new byte[count];
        System.arraycopy(buffer, offset, response, 0, count);
        return response;
    }

    public ByteString toByteString() {
        return new ByteString(this.getBytes(0, this.length));
    }

    /**
     * Return hexadecimal string for content of ByteBuffer
     *
     * @return Hexadecimal string
     */
    public String toString() {
        return TlvHexString.hexifyByteArray(buffer, ' ', length);
    }
}
