import * as React from 'react';
import AppBar from '@mui/material/AppBar';
import Box from '@mui/material/Box';
import Toolbar from '@mui/material/Toolbar';
import Typography from '@mui/material/Typography';
import Button from '@mui/material/Button';
import IconButton from '@mui/material/IconButton';
import MenuIcon from '@mui/icons-material/Menu';
import Input from '@mui/material/Input';
import { TextField } from '@mui/material';
import UploadForm from './UploadForm';

export default function NavBar({handleAddFile, handleFileUpload}) {

  return (
    <Box sx={{ flexGrow: 1 }}>
      <AppBar position="static">
        <Toolbar>
          {/* <IconButton
            size="large"
            edge="start"
            color="inherit"
            aria-label="menu"
            sx={{ mr: 2 }}
          >
            <MenuIcon />
          </IconButton> */}
          <Typography variant="h6" component="div" sx={{ flexGrow: 1 }}>
            Image Uploader
          </Typography>
            <UploadForm handleFileUpload={handleFileUpload} handleAddFile={handleAddFile} />
        </Toolbar>
      </AppBar>
    </Box>
  );
}