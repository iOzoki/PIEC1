const volume = document.getElementById('volume')
const bass = document.getElementById('bass')
const mid = document.getElementById('mid')
const treble = document.getElementById('treble')
const startRecordButton = document.getElementById('startRecord');
const stopRecordButton = document.getElementById('stopRecord');
const downloadLink = document.getElementById('downloadLink');
let mediaRecorder;
let audioChunks = [];

const context = new AudioContext()
const analyserNode = new AnalyserNode(context, { fftSize: 256 })
const gainNode = new GainNode(context, { gain: volume.value})
const bassEQ = new BiquadFilterNode(context, {
    type: 'lowshelf',
    frequency: 500,
    gain: bass.value
})
const midEQ = new BiquadFilterNode(context, {
    type: 'peaking',
    Q: Math.SQRT1_2,
    frequency: 1500,
    gain: mid.value
})
const trebleEQ = new BiquadFilterNode(context, {
    type: 'highshelf',
    frequency: 3000,
    gain: treble.value
})

setupEventListeners()
setupContext()

function setupEventListeners() {
    volume.addEventListener('input', e => {
        const value = parseFloat(e.target.value)
        gainNode.gain.setTargetAtTime(value, context.currentTime, .01)
    })

    bass.addEventListener('input', e => {
        const value = parseInt(e.target.value)
        bassEQ.gain.setTargetAtTime(value, context.currentTime, .01)
    })

    mid.addEventListener('input', e => {
        const value = parseInt(e.target.value)
        midEQ.gain.setTargetAtTime(value, context.currentTime, .01)
    })

    treble.addEventListener('input', e => {
        const value = parseInt(e.target.value)
        trebleEQ.gain.setTargetAtTime(value, context.currentTime, .01)
    })
}

async function setupContext() {
    const guitar = await getGuitar()
    if (context.state === 'suspended') {
        await context.resume()
    }
    const source = context.createMediaStreamSource(guitar)
    source
        .connect(bassEQ)
        .connect(midEQ)
        .connect(trebleEQ)
        .connect(gainNode)
        .connect(analyserNode)
        .connect(context.destination)
}

function getGuitar() {
    return navigator.mediaDevices.getUserMedia({
        audio: {
            echoCancellation: false,
            autoGainControl: false,
            noiseSuppression: false,
            latency: 0
        }
    })
}

startRecordButton.addEventListener('click', async () => {
    const stream = await navigator.mediaDevices.getUserMedia({ audio: true });

    mediaRecorder = new MediaRecorder(stream);

    mediaRecorder.ondataavailable = (event) => {
        audioChunks.push(event.data);
    };

    mediaRecorder.onstop = () => {
        const audioBlob = new Blob(audioChunks, { type: 'audio/wav' });
        const formData = new FormData();
        formData.append('file', audioBlob, 'audio.wav');

        fetch('http://localhost:8080/upload-audio', {
            method: 'POST',
            body: formData
        })
            .then(response => response.json())
            .then(data => {
                console.log('Notas capturadas:', data);
            })
            .catch(error => console.error('Erro no upload:', error));

        audioChunks = [];
    };

    mediaRecorder.start();
    startRecordButton.disabled = true;
    stopRecordButton.disabled = false;
});

stopRecordButton.addEventListener('click', () => {
    mediaRecorder.stop();
    startRecordButton.disabled = false;
    stopRecordButton.disabled = true;
});
