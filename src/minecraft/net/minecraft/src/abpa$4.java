/*
 * Copyright 2015 Eiren 'Eirenliel' Rain and GreenCubes.org
 * authors
 *
 * Permission is hereby granted, free of charge, to any person
 * obtaining a copy of this software and associated
 * documentation files (the "Software"), to deal in the
 * Software without restriction, including without limitation
 * the rights to use, copy, modify, merge, publish, distribute,
 * sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do
 * so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall
 * be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
 * OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE
 * OR OTHER DEALINGS IN THE SOFTWARE.
 */
package net.minecraft.src;

public class abpa$4 implements Runnable {

	private final NetClientHandler netClientHandler;
	private final Packet55MultiChunk packet;

	public abpa$4(NetClientHandler netClientHandler, Packet55MultiChunk packet) {
		this.netClientHandler = netClientHandler;
		this.packet = packet;
	}

	@Override
	public void run() {
		int l = 0;
		for(int i = 0; i < packet.chunksCoordinates.size(); ++i) {
			ChunkCoordIntPair coord = packet.chunksCoordinates.get(i);
			netClientHandler.worldClient.invalidateBlockReceiveRegion(coord.chunkXPos * 16, 0, coord.chunkZPos * 16, coord.chunkXPos * 16 + 15, 127, coord.chunkZPos * 16 + 15);
			l = netClientHandler.worldClient.addFullChunkData(coord.chunkXPos, coord.chunkZPos, packet.data, l);
		}
	}

}
