package com.sentaroh.android.JcifsFile2;

/*
The MIT License (MIT)
Copyright (c) 2011-2018 Sentaroh

Permission is hereby granted, free of charge, to any person obtaining a copy of
this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights to use,
copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software,
and to permit persons to whom the Software is furnished to do so, subject to
the following conditions:

The above copyright notice and this permission notice shall be included in all copies or
substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR
PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT,
TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
OTHER DEALINGS IN THE SOFTWARE.

*/

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;


public class JcifsFile {

    final static public int JCIFS_FILE_SMB1 = JcifsAuth.JCIFS_FILE_SMB1;
    final static public int JCIFS_FILE_SMB212 = JcifsAuth.JCIFS_FILE_SMB212;

    private int mSmbLevel = JCIFS_FILE_SMB1;

    private JcifsAuth mAuth = null;

    private jcifsng212.smb.SmbFile mSmb212File = null;
    private jcifs.smb.SmbFile mSmb1File = null;

    public JcifsFile(String url, JcifsAuth auth) throws MalformedURLException, JcifsException {
        if (auth==null) {
            throw new JcifsException("JcifsAuth is null.");
        }
        mSmbLevel = auth.getSmbLevel();
        mAuth = auth;

        if (mSmbLevel==JCIFS_FILE_SMB1) {
            mSmb1File = new jcifs.smb.SmbFile(url, auth.getSmb1Auth());
        } else if (mSmbLevel==JCIFS_FILE_SMB212) {
            mSmb212File = new jcifsng212.smb.SmbFile(url, auth.getSmb212Auth());
        }
    }

    private JcifsFile(jcifs.smb.SmbFile smb1File, JcifsAuth auth) throws JcifsException {
        mSmbLevel = JCIFS_FILE_SMB1;
        if (auth==null || !auth.isSmb1()) {
            throw new JcifsException("JcifsAuth is null or SMB2.");
        }
        mAuth = auth;
        mSmb1File = smb1File;
    }

    private JcifsFile(jcifsng212.smb.SmbFile smb212File, JcifsAuth auth) throws JcifsException {
        mSmbLevel = JCIFS_FILE_SMB212;
        if (auth==null || auth.isSmb1()) {
            throw new JcifsException("JcifsAuth is null or SMB1.");
        }
        mAuth = auth;
        mSmb212File = smb212File;
    }

    public boolean isSmb1File() {
        return mSmbLevel==JCIFS_FILE_SMB1;
    }

    public boolean isSmb212File() {
        return mSmbLevel==JCIFS_FILE_SMB212;
    }

    public jcifs.smb.SmbFile getSmb1File() {
        return mSmb1File;
    }

    public jcifsng212.smb.SmbFile getSmb212File() {
        return mSmb212File;
    }

    public boolean exists() throws JcifsException {
        try {
            if (mSmbLevel==JCIFS_FILE_SMB1) {
                return mSmb1File.exists();
            } else if (mSmbLevel==JCIFS_FILE_SMB212) {
                return mSmb212File.exists();
            } else 
            	throw (new JcifsException(JcifsException.NT_STATUS_DESC_INVALID_JCIFS_LEVEL, JcifsException.NT_STATUS_INT_INVALID_JCIFS_LEVEL));
        } catch (jcifsng212.smb.SmbException e) {
            throw (new JcifsException(e, e.getNtStatus(), e.getCause()));
        } catch (jcifs.smb.SmbException e) {
            throw (new JcifsException(e, e.getNtStatus(), e.getCause()));
        }

    }

    public void delete() throws JcifsException {
        try {
            if (mSmbLevel==JCIFS_FILE_SMB1) {
            	mSmb1File.delete();
            } else if (mSmbLevel==JCIFS_FILE_SMB212) {
            	mSmb212File.delete();
            } else 
            	throw (new JcifsException(JcifsException.NT_STATUS_DESC_INVALID_JCIFS_LEVEL, JcifsException.NT_STATUS_INT_INVALID_JCIFS_LEVEL));
        } catch (jcifsng212.smb.SmbException e) {
            throw (new JcifsException(e, e.getNtStatus(), e.getCause()));
        } catch (jcifs.smb.SmbException e) {
            throw (new JcifsException(e, e.getNtStatus(), e.getCause()));
        }

    }

    public void mkdir() throws JcifsException {
        try {
            if (mSmbLevel==JCIFS_FILE_SMB1) {
            	mSmb1File.mkdir();
            } else if (mSmbLevel==JCIFS_FILE_SMB212) {
            	mSmb212File.mkdir();
            } else 
            	throw (new JcifsException(JcifsException.NT_STATUS_DESC_INVALID_JCIFS_LEVEL, JcifsException.NT_STATUS_INT_INVALID_JCIFS_LEVEL));
        } catch (jcifsng212.smb.SmbException e) {
            throw (new JcifsException(e, e.getNtStatus(), e.getCause()));
        } catch (jcifs.smb.SmbException e) {
            throw (new JcifsException(e, e.getNtStatus(), e.getCause()));
        }
    }

    public void mkdirs() throws JcifsException {
        try {
            if (mSmbLevel==JCIFS_FILE_SMB1) {
            	mSmb1File.mkdirs();
            } else if (mSmbLevel==JCIFS_FILE_SMB212) {
            	mSmb212File.mkdirs();
            } else 
            	throw (new JcifsException(JcifsException.NT_STATUS_DESC_INVALID_JCIFS_LEVEL, JcifsException.NT_STATUS_INT_INVALID_JCIFS_LEVEL));
        } catch (jcifsng212.smb.SmbException e) {
            throw (new JcifsException(e, e.getNtStatus(), e.getCause()));
        } catch (jcifs.smb.SmbException e) {
            throw (new JcifsException(e, e.getNtStatus(), e.getCause()));
        }
    }

    public int getAttributes() throws JcifsException {
        try {
            if (mSmbLevel==JCIFS_FILE_SMB1) {
            	return mSmb1File.getAttributes();
            } else if (mSmbLevel==JCIFS_FILE_SMB212) {
            	return mSmb212File.getAttributes();
            } else 
            	throw (new JcifsException(JcifsException.NT_STATUS_DESC_INVALID_JCIFS_LEVEL, JcifsException.NT_STATUS_INT_INVALID_JCIFS_LEVEL));
        } catch (jcifsng212.smb.SmbException e) {
            throw (new JcifsException(e, e.getNtStatus(), e.getCause()));
        } catch (jcifs.smb.SmbException e) {
            throw (new JcifsException(e, e.getNtStatus(), e.getCause()));
        }
    }

    public InputStream getInputStream() throws JcifsException {
        try {
            if (mSmbLevel==JCIFS_FILE_SMB1) {
            	return mSmb1File.getInputStream();
            } else if (mSmbLevel==JCIFS_FILE_SMB212) {
            	return mSmb212File.getInputStream();
            } else 
            	throw (new JcifsException(JcifsException.NT_STATUS_DESC_INVALID_JCIFS_LEVEL, JcifsException.NT_STATUS_INT_INVALID_JCIFS_LEVEL));
        } catch (jcifsng212.smb.SmbException e) {
            throw (new JcifsException(e, e.getNtStatus(), e.getCause()));
        } catch (jcifs.smb.SmbException e) {
            throw (new JcifsException(e, e.getNtStatus(), e.getCause()));
        } catch (IOException e) {
            throw (new JcifsException(e, 0, e.getCause()));
        }
    }


    public OutputStream getOutputStream() throws JcifsException {
        try {
            if (mSmbLevel==JCIFS_FILE_SMB1) {
            	return mSmb1File.getOutputStream();
            } else if (mSmbLevel==JCIFS_FILE_SMB212) {
            	return mSmb212File.getOutputStream();
            } else 
            	throw (new JcifsException(JcifsException.NT_STATUS_DESC_INVALID_JCIFS_LEVEL, JcifsException.NT_STATUS_INT_INVALID_JCIFS_LEVEL));
        } catch (jcifsng212.smb.SmbException e) {
            throw (new JcifsException(e, e.getNtStatus(), e.getCause()));
        } catch (jcifs.smb.SmbException e) {
            throw (new JcifsException(e, e.getNtStatus(), e.getCause()));
        } catch (IOException e) {
            throw (new JcifsException(e, 0, e.getCause()));
        }
    }

    public void close() throws JcifsException {
        if (mSmbLevel==JCIFS_FILE_SMB1) {
        	//Nop for JCIFS(SMB1)
//        	mSmb1File.close();
        } else if (mSmbLevel==JCIFS_FILE_SMB212) {
        	mSmb212File.close();
        } else 
        	throw (new JcifsException(JcifsException.NT_STATUS_DESC_INVALID_JCIFS_LEVEL, JcifsException.NT_STATUS_INT_INVALID_JCIFS_LEVEL));
    }

    public void connect() throws JcifsException {
        try {
            if (mSmbLevel==JCIFS_FILE_SMB1) {
            	mSmb1File.connect();
            } else if (mSmbLevel==JCIFS_FILE_SMB212) {
            	mSmb212File.connect();
            } else 
            	throw (new JcifsException(JcifsException.NT_STATUS_DESC_INVALID_JCIFS_LEVEL, JcifsException.NT_STATUS_INT_INVALID_JCIFS_LEVEL));
        } catch (jcifsng212.smb.SmbException e) {
            throw (new JcifsException(e, e.getNtStatus(), e.getCause()));
        } catch (jcifs.smb.SmbException e) {
            throw (new JcifsException(e, e.getNtStatus(), e.getCause()));
        } catch (IOException e) {
            throw (new JcifsException(e, 0, e.getCause()));
        }
    }

    public void createNew() throws JcifsException {
        try {
            if (mSmbLevel==JCIFS_FILE_SMB1) {
            	mSmb1File.createNewFile();
            } else if (mSmbLevel==JCIFS_FILE_SMB212) {
            	mSmb212File.createNewFile();
            } else 
            	throw (new JcifsException(JcifsException.NT_STATUS_DESC_INVALID_JCIFS_LEVEL, JcifsException.NT_STATUS_INT_INVALID_JCIFS_LEVEL));
        } catch (jcifsng212.smb.SmbException e) {
            throw (new JcifsException(e, e.getNtStatus(), e.getCause()));
        } catch (jcifs.smb.SmbException e) {
            throw (new JcifsException(e, e.getNtStatus(), e.getCause()));
        }
    }

    public String getName() {
        if (mSmbLevel==JCIFS_FILE_SMB1) {
        	return mSmb1File.getName();
        } else if (mSmbLevel==JCIFS_FILE_SMB212) {
        	return mSmb212File.getName();
        }
        return null;
    }

    public String getPath() {
        if (mSmbLevel==JCIFS_FILE_SMB1) {
        	return mSmb1File.getPath();
        } else if (mSmbLevel==JCIFS_FILE_SMB212) {
        	return mSmb212File.getPath();
        }
        return null;
    }

    public String getCanonicalPath() {
        if (mSmbLevel==JCIFS_FILE_SMB1) {
        	return mSmb1File.getCanonicalPath();
        } else if (mSmbLevel==JCIFS_FILE_SMB212) {
        	return mSmb212File.getCanonicalPath();
        }
        return null;
    }

    public String getShare() {
        if (mSmbLevel==JCIFS_FILE_SMB1) {
        	return mSmb1File.getShare();
        } else if (mSmbLevel==JCIFS_FILE_SMB212) {
        	return mSmb212File.getShare();
        }
        return null;
    }

    public int getType() throws JcifsException {
        try {
            if (mSmbLevel==JCIFS_FILE_SMB1) {
            	return mSmb1File.getType();
            } else if (mSmbLevel==JCIFS_FILE_SMB212) {
            	return mSmb212File.getType();
            } else 
            	throw (new JcifsException(JcifsException.NT_STATUS_DESC_INVALID_JCIFS_LEVEL, JcifsException.NT_STATUS_INT_INVALID_JCIFS_LEVEL));
        } catch (jcifsng212.smb.SmbException e) {
            throw (new JcifsException(e, e.getNtStatus(), e.getCause()));
        } catch (jcifs.smb.SmbException e) {
            throw (new JcifsException(e, e.getNtStatus(), e.getCause()));
        }
    }

    public String getUncPath() {
        if (mSmbLevel==JCIFS_FILE_SMB1) {
        	return mSmb1File.getUncPath();
        } else if (mSmbLevel==JCIFS_FILE_SMB212) {
        	return mSmb212File.getUncPath();
        }
        return null;
    }

    public String getParent() {
        if (mSmbLevel==JCIFS_FILE_SMB1) {
        	return mSmb1File.getParent();
        } else if (mSmbLevel==JCIFS_FILE_SMB212) {
        	return mSmb212File.getParent();
        }
        return null;
    }

    public boolean canRead() throws JcifsException {
        try {
            if (mSmbLevel==JCIFS_FILE_SMB1) {
            	return mSmb1File.canRead();
            } else if (mSmbLevel==JCIFS_FILE_SMB212) {
            	return mSmb212File.canRead();
            } else 
            	throw (new JcifsException(JcifsException.NT_STATUS_DESC_INVALID_JCIFS_LEVEL, JcifsException.NT_STATUS_INT_INVALID_JCIFS_LEVEL));
        } catch (jcifsng212.smb.SmbException e) {
            throw (new JcifsException(e, e.getNtStatus(), e.getCause()));
        } catch (jcifs.smb.SmbException e) {
            throw (new JcifsException(e, e.getNtStatus(), e.getCause()));
        }
    }

    public boolean canWrite() throws JcifsException {
        try {
            if (mSmbLevel==JCIFS_FILE_SMB1) {
            	return mSmb1File.canWrite();
            } else if (mSmbLevel==JCIFS_FILE_SMB212) {
            	return mSmb212File.canWrite();
            } else 
            	throw (new JcifsException(JcifsException.NT_STATUS_DESC_INVALID_JCIFS_LEVEL, JcifsException.NT_STATUS_INT_INVALID_JCIFS_LEVEL));
        } catch (jcifsng212.smb.SmbException e) {
            throw (new JcifsException(e, e.getNtStatus(), e.getCause()));
        } catch (jcifs.smb.SmbException e) {
            throw (new JcifsException(e, e.getNtStatus(), e.getCause()));
        }
    }

    public boolean isDirectory() throws JcifsException {
        try {
            if (mSmbLevel==JCIFS_FILE_SMB1) {
            	return mSmb1File.isDirectory();
            } else if (mSmbLevel==JCIFS_FILE_SMB212) {
            	return mSmb212File.isDirectory();
            } else 
            	throw (new JcifsException(JcifsException.NT_STATUS_DESC_INVALID_JCIFS_LEVEL, JcifsException.NT_STATUS_INT_INVALID_JCIFS_LEVEL));
        } catch (jcifsng212.smb.SmbException e) {
            throw (new JcifsException(e, e.getNtStatus(), e.getCause()));
        } catch (jcifs.smb.SmbException e) {
            throw (new JcifsException(e, e.getNtStatus(), e.getCause()));
        }
    }

    public boolean isFile() throws JcifsException {
        try {
            if (mSmbLevel==JCIFS_FILE_SMB1) {
            	return mSmb1File.isFile();
            } else if (mSmbLevel==JCIFS_FILE_SMB212) {
            	return mSmb212File.isFile();
            } else 
            	throw (new JcifsException(JcifsException.NT_STATUS_DESC_INVALID_JCIFS_LEVEL, JcifsException.NT_STATUS_INT_INVALID_JCIFS_LEVEL));
        } catch (jcifsng212.smb.SmbException e) {
            throw (new JcifsException(e, e.getNtStatus(), e.getCause()));
        } catch (jcifs.smb.SmbException e) {
            throw (new JcifsException(e, e.getNtStatus(), e.getCause()));
        }
    }

    public boolean isHidden() throws JcifsException {
        try {
            if (mSmbLevel==JCIFS_FILE_SMB1) {
            	return mSmb1File.isHidden();
            } else if (mSmbLevel==JCIFS_FILE_SMB212) {
            	return mSmb212File.isHidden();
            } else 
            	throw (new JcifsException(JcifsException.NT_STATUS_DESC_INVALID_JCIFS_LEVEL, JcifsException.NT_STATUS_INT_INVALID_JCIFS_LEVEL));
        } catch (jcifsng212.smb.SmbException e) {
            throw (new JcifsException(e, e.getNtStatus(), e.getCause()));
        } catch (jcifs.smb.SmbException e) {
            throw (new JcifsException(e, e.getNtStatus(), e.getCause()));
        }
    }

    public long length() throws JcifsException {
        try {
            if (mSmbLevel==JCIFS_FILE_SMB1) {
            	return mSmb1File.length();
            } else if (mSmbLevel==JCIFS_FILE_SMB212) {
            	return mSmb212File.length();
            } else 
            	throw (new JcifsException(JcifsException.NT_STATUS_DESC_INVALID_JCIFS_LEVEL, JcifsException.NT_STATUS_INT_INVALID_JCIFS_LEVEL));
        } catch (jcifsng212.smb.SmbException e) {
            throw (new JcifsException(e, e.getNtStatus(), e.getCause()));
        } catch (jcifs.smb.SmbException e) {
            throw (new JcifsException(e, e.getNtStatus(), e.getCause()));
        }
    }

    public String[] list() throws JcifsException {
        try {
            if (mSmbLevel==JCIFS_FILE_SMB1) {
            	return mSmb1File.list();
            } else if (mSmbLevel==JCIFS_FILE_SMB212) {
            	return mSmb212File.list();
            } else 
            	throw (new JcifsException(JcifsException.NT_STATUS_DESC_INVALID_JCIFS_LEVEL, JcifsException.NT_STATUS_INT_INVALID_JCIFS_LEVEL));
        } catch (jcifsng212.smb.SmbException e) {
            throw (new JcifsException(e, e.getNtStatus(), e.getCause()));
        } catch (jcifs.smb.SmbException e) {
            throw (new JcifsException(e, e.getNtStatus(), e.getCause()));
        }
    }

    public JcifsFile[] listFiles() throws JcifsException {
        try {
            if (mSmbLevel==JCIFS_FILE_SMB1) {
                jcifs.smb.SmbFile[] smb1Files = mSmb1File.listFiles();
                if (smb1Files == null) return null;
                JcifsFile[] result = new JcifsFile[smb1Files.length];
                for (int i = 0; i < smb1Files.length; i++)
                    result[i] = new JcifsFile(smb1Files[i], mAuth);
                return result;
            } else if (mSmbLevel==JCIFS_FILE_SMB212) {
                jcifsng212.smb.SmbFile[] smb212Files = mSmb212File.listFiles();
                if (smb212Files == null) return null;
                JcifsFile[] result = new JcifsFile[smb212Files.length];
                for (int i = 0; i < smb212Files.length; i++)
                    result[i] = new JcifsFile(smb212Files[i], mAuth);
                return result;
            } else 
            	throw (new JcifsException(JcifsException.NT_STATUS_DESC_INVALID_JCIFS_LEVEL, JcifsException.NT_STATUS_INT_INVALID_JCIFS_LEVEL));
        } catch (jcifsng212.smb.SmbException e) {
            throw (new JcifsException(e, e.getNtStatus(), e.getCause()));
        } catch (jcifs.smb.SmbException e) {
            throw (new JcifsException(e, e.getNtStatus(), e.getCause()));
        }
    }

    public void renameTo(JcifsFile d) throws JcifsException {
        try {
            if (mSmbLevel==JCIFS_FILE_SMB1) {
                if (d.getSmb1File() == null)
                    throw new JcifsException("Null SMB1 file specified.");
                else mSmb1File.renameTo(d.getSmb1File());
            } else if (mSmbLevel==JCIFS_FILE_SMB212) {
                if (d.getSmb212File() == null)
                    throw new JcifsException("Null SMB212 file specified.");
                else mSmb212File.renameTo(d.getSmb212File());
            } else 
            	throw (new JcifsException(JcifsException.NT_STATUS_DESC_INVALID_JCIFS_LEVEL, JcifsException.NT_STATUS_INT_INVALID_JCIFS_LEVEL));
        } catch (jcifsng212.smb.SmbException e) {
            throw (new JcifsException(e, e.getNtStatus(), e.getCause()));
        } catch (jcifs.smb.SmbException e) {
            throw (new JcifsException(e, e.getNtStatus(), e.getCause()));
        }
    }

    public JcifsAuth getAuth() {
        return mAuth;
    }


    public void setLastModified(long lm) throws JcifsException {
        try {
            if (mSmbLevel==JCIFS_FILE_SMB1) {
            	mSmb1File.setLastModified(lm);
            } else if (mSmbLevel==JCIFS_FILE_SMB212) {
            	mSmb212File.setLastModified(lm);
            } else 
            	throw (new JcifsException(JcifsException.NT_STATUS_DESC_INVALID_JCIFS_LEVEL, JcifsException.NT_STATUS_INT_INVALID_JCIFS_LEVEL));
        } catch (jcifsng212.smb.SmbException e) {
            throw (new JcifsException(e, e.getNtStatus(), e.getCause()));
        } catch (jcifs.smb.SmbException e) {
            throw (new JcifsException(e, e.getNtStatus(), e.getCause()));
        }
    }

    public long getLastModified() throws JcifsException {
        try {
            if (mSmbLevel==JCIFS_FILE_SMB1) {
            	return mSmb1File.lastModified();
            } else if (mSmbLevel==JCIFS_FILE_SMB212) {
            	return mSmb212File.lastModified();
            } else 
            	throw (new JcifsException(JcifsException.NT_STATUS_DESC_INVALID_JCIFS_LEVEL, JcifsException.NT_STATUS_INT_INVALID_JCIFS_LEVEL));
        } catch (jcifsng212.smb.SmbException e) {
            throw (new JcifsException(e, e.getNtStatus(), e.getCause()));
        } catch (jcifs.smb.SmbException e) {
            throw (new JcifsException(e, e.getNtStatus(), e.getCause()));
        }
    }

}
