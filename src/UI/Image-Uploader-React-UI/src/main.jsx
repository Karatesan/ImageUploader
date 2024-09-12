import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import App from './App.jsx'
import ImageUploader from './components/ImageUploader.jsx'

createRoot(document.getElementById('root')).render(
  <StrictMode>
    <ImageUploader />
  </StrictMode>,
)
