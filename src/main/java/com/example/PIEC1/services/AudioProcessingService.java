package com.example.PIEC1.services;

import be.tarsos.dsp.AudioDispatcher;
import be.tarsos.dsp.io.jvm.AudioDispatcherFactory;
import be.tarsos.dsp.WaveformSimilarityBasedOverlapAdd;
import be.tarsos.dsp.pitch.PitchDetectionResult;
import be.tarsos.dsp.pitch.PitchProcessor;
import be.tarsos.dsp.pitch.PitchProcessor.PitchEstimationAlgorithm;
import be.tarsos.dsp.pitch.PitchDetectionHandler;
import be.tarsos.dsp.AudioEvent;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Service
public class AudioProcessingService {

    public List<String> processAudio(MultipartFile file) throws Exception {
        InputStream inputStream = file.getInputStream();

        File tempFile = File.createTempFile("audio", ".wav");
        tempFile.deleteOnExit();

        try (FileOutputStream out = new FileOutputStream(tempFile)) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }
        }

        AudioDispatcher dispatcher = AudioDispatcherFactory.fromPipe(
                tempFile.getAbsolutePath(), 44100, 1024, 512
        );

        private String convertFrequencyToNote(float frequency) {
            String[] noteNames = {"C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B" };
        }


        List<String> notes = new ArrayList<>();

        return notes;
    }
}