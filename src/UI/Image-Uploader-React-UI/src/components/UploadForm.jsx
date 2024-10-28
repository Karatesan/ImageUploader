import { Button, TextField } from "@mui/material";
import React, { useRef } from "react";

export default function UploadForm({ handleFileUpload, handleAddFile }) {

  const hiddenFileInput = useRef(null);

  const handleHiddenInputClick = () => {
    hiddenFileInput.current.click();
  };

  return (
    <form>
      <input
        type="file"
        onChange={handleAddFile}
        style={{ display: "none" }}
        ref={hiddenFileInput}
        accept="image/*"
      />
      <Button
        onClick={handleHiddenInputClick}
        variant="contained"
        color="primary"
        component="span"
      >
        Choose image
      </Button>
      <Button
        onClick={handleFileUpload}
        variant="contained"
        color="primary"
        component="span"
      >
        Upload
      </Button>
    </form>
  );
}
