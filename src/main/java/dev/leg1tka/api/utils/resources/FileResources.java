package dev.leg1tka.api.utils.resources;

import dev.leg1tka.modules.*;
import dev.leg1tka.modules.combat.*;
import dev.leg1tka.modules.movement.*;
import dev.leg1tka.modules.render.*;
import dev.leg1tka.modules.player.*;
import dev.leg1tka.modules.misc.*;
import dev.leg1tka.settings.*;
import dev.leg1tka.managers.*;
import dev.leg1tka.gui.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import lombok.Generated;

public final class FileResources {
   public static void a(String string0) throws IOException {
      Path path1 = Paths.get(string0);
      if (Files.exists(path1)) {
         new File(string0).delete();
      }

      Files.createFile(path1);
   }

   public static boolean b(String string0) {
      return Files.exists(Paths.get(string0));
   }

   @Generated
   private FileResources() {
      throw new UnsupportedOperationException("This ServerHelper a utility class and cannot be instantiated");
   }
}
