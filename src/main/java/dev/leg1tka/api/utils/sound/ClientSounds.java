package dev.leg1tka.api.utils.sound;

import dev.leg1tka.modules.*;
import dev.leg1tka.modules.combat.*;
import dev.leg1tka.modules.movement.*;
import dev.leg1tka.modules.render.*;
import dev.leg1tka.modules.player.*;
import dev.leg1tka.modules.misc.*;
import dev.leg1tka.settings.*;
import dev.leg1tka.managers.*;
import dev.leg1tka.gui.*;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.sound.sampled.LineEvent.Type;
import lombok.Generated;

public final class ClientSounds {
   private static final ExecutorService a = Executors.newSingleThreadExecutor(runnable0 -> {
      Thread thread1 = new Thread(runnable0, "AspectDLC-ClientSounds");
      thread1.setDaemon(true);
      return thread1;
   });

   public static void a(String string0, double doubleValue1, float floatValue3) {
      a.execute(() -> b(string0, doubleValue1, floatValue3));
   }

   private static void b(String string0, double doubleValue1, float floatValue3) {
      String string4 = "/assets/" + dev.leg1tka.api.utils.resources.NamespaceResolver.a() + "/sounds/" + string0;

      try {
         try (InputStream inputStream5 = dev.leg1tka.api.utils.sound.ClientSounds.class.getResourceAsStream(string4)) {
            if (inputStream5 != null) {
               try (
                  BufferedInputStream bufferedInputStream6 = new BufferedInputStream(inputStream5);
                  AudioInputStream audioInputStream7 = AudioSystem.getAudioInputStream(bufferedInputStream6);
                  AudioInputStream audioInputStream8 = a(audioInputStream7, floatValue3);
               ) {
                  Clip clip9 = AudioSystem.getClip();
                  clip9.addLineListener(lineEvent1x -> {
                     if (lineEvent1x.getType() == Type.STOP) {
                        clip9.close();
                     }
                  });
                  clip9.open(audioInputStream8);
                  a(clip9, doubleValue1);
                  clip9.start();
                  return;
               }
            }
         }
      } catch (IOException | LineUnavailableException | UnsupportedAudioFileException exception19) {
      }
   }

   private static AudioInputStream a(AudioInputStream audioInputStream0, float floatValue1) throws IOException {
      AudioFormat audioFormat2 = audioInputStream0.getFormat();
      byte[] byteValueArray3 = audioInputStream0.readAllBytes();
      float floatValue4 = audioFormat2.getSampleRate() * Math.max(0.5F, Math.min(2.0F, floatValue1));
      AudioFormat audioFormat5 = new AudioFormat(floatValue4, audioFormat2.getSampleSizeInBits(), audioFormat2.getChannels(), true, audioFormat2.isBigEndian());
      return new AudioInputStream(new ByteArrayInputStream(byteValueArray3), audioFormat5, (long)(byteValueArray3.length / audioFormat5.getFrameSize()));
   }

   private static void a(Clip clip0, double doubleValue1) {
      if (clip0.isControlSupported(javax.sound.sampled.FloatControl.Type.MASTER_GAIN)) {
         double doubleValue3 = Math.max(0.0, Math.min(1.0, doubleValue1));
         FloatControl floatControl5 = (FloatControl)clip0.getControl(javax.sound.sampled.FloatControl.Type.MASTER_GAIN);
         float floatValue6 = (float)(Math.log10(doubleValue3 <= 0.0 ? 1.0E-4 : doubleValue3) * 20.0);
         floatControl5.setValue(floatValue6);
      }
   }

   @Generated
   private ClientSounds() {
      throw new UnsupportedOperationException("This ServerHelper a utility class and cannot be instantiated");
   }
}
