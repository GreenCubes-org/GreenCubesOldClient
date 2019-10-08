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

public class abpa$3 implements Runnable {

	private final NetClientHandler netClientHandler;
	private final Packet51MapChunk packet51mapchunk;

	public abpa$3(NetClientHandler netClientHandler, Packet51MapChunk packet51mapchunk) {
		this.netClientHandler = netClientHandler;
		this.packet51mapchunk = packet51mapchunk;
	}

	@Override
	public void run() {
		netClientHandler.worldClient.invalidateBlockReceiveRegion(packet51mapchunk.xPosition, packet51mapchunk.yPosition, packet51mapchunk.zPosition, (packet51mapchunk.xPosition + packet51mapchunk.xSize) - 1, (packet51mapchunk.yPosition + packet51mapchunk.ySize) - 1, (packet51mapchunk.zPosition + packet51mapchunk.zSize) - 1);
		if(packet51mapchunk.isFull)
			netClientHandler.worldClient.setFullChunkData(packet51mapchunk.xPosition >> 4, packet51mapchunk.zPosition >> 4, packet51mapchunk.chunk);
		else
			netClientHandler.worldClient.setChunkData(packet51mapchunk.xPosition, packet51mapchunk.yPosition, packet51mapchunk.zPosition, packet51mapchunk.xSize, packet51mapchunk.ySize, packet51mapchunk.zSize, packet51mapchunk.chunk);

	}

}
