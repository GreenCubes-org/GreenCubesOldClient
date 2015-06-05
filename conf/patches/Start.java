import java.io.File;
import java.lang.reflect.Field;

import net.minecraft.client.Minecraft;

public class Start
{
	public static void main(String[] args)
	{
		Minecraft.start(new String[] {"--directory", ".", "--player", "Rena4ka"});
	}
}
