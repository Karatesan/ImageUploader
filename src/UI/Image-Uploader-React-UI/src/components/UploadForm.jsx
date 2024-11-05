import { Button, Dialog, DialogActions, DialogContent, DialogContentText, DialogTitle, TextField } from "@mui/material";
import React, { useRef } from "react";

export default function UploadForm({ handleFileUpload, handleAddFile, canUpload }) {

  const [openDialog,setOpenDialog] = React.useState(false)
  const hiddenFileInput = useRef(null);

  const handleClickOpen = () => {
    if(canUpload)
      setOpenDialog(true);
  };

  const handleClose = () => {
    setOpenDialog(false);
  };

  

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
        multiple
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
        onClick={handleClickOpen}
        variant="contained"
        color="primary"
        component="span"
        disabled={!canUpload}
      >
        Upload
      </Button>
      <Dialog
        open={openDialog}
        onClose={handleClose}
        PaperProps={{
          component: 'form',
          onSubmit: (event) => {
            event.preventDefault();
            const formData = new FormData(event.currentTarget);
            const formJson = Object.fromEntries(formData.entries());
            const galleryName = formJson.galleryName;
            handleFileUpload(galleryName);
            handleClose();     
          },
        }}
      >
        <DialogTitle>Name Your Gallery</DialogTitle>
        <DialogContent>
          <DialogContentText>
            How would you like to name your gallery?
          </DialogContentText>
          <TextField
            autoFocus
            required
            margin="dense"
            id="galleryName"
            name="galleryName"
            label="Gallery Name"
            type="text"
            fullWidth
            variant="standard"
          />
        </DialogContent>
        <DialogActions>
          <Button onClick={()=>{handleClose(); handleFileUpload()}}>Cancel</Button>
          <Button type="submit">Upload</Button>
        </DialogActions>
      </Dialog>
    </form>
  );
}