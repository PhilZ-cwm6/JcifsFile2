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

import java.util.Properties;

public class JcifsAuth {
    final static public int JCIFS_FILE_SMB1 = 1;
    final static public int JCIFS_FILE_SMB212 = 2;
    private jcifs.smb.NtlmPasswordAuthentication mSmb1Auth = null;
    private jcifsng212.CIFSContext mSmb212Auth = null;
    private int mSmbLevel = JCIFS_FILE_SMB1;

    private String mDomain = null, mUserName = null, mUserPass = null;

    /**
     * SMB1 or SMB2 Constructor
     *
     * @param smb1   1 is use jcifs-1.3.17, 2 is jcifs-ng
     * @param domain A domain name
     * @param user   A user name
     * @param pass   A password for user
     * @throws JcifsException
     */
    @SuppressWarnings("deprecation")
    public JcifsAuth(int smb_level, String domain, String user, String pass) {
        mSmbLevel = smb_level;
        mDomain = domain;
        mUserName = user;
        mUserPass = pass;
        if (mSmbLevel==JCIFS_FILE_SMB1) {
            mSmb1Auth = new jcifs.smb.NtlmPasswordAuthentication(domain, user, pass);
        } else if (mSmbLevel==JCIFS_FILE_SMB212) {
            try {
                Properties prop = new Properties();
                prop.setProperty("jcifs.smb.client.minVersion", "SMB202");
                prop.setProperty("jcifs.smb.client.maxVersion", "SMB300");
                jcifsng212.context.BaseContext bc = new jcifsng212.context.BaseContext(new jcifsng212.config.PropertyConfiguration(prop));
                jcifsng212.smb.NtlmPasswordAuthentication creds = new jcifsng212.smb.NtlmPasswordAuthentication(bc, domain, user, pass);
                mSmb212Auth = bc.withCredentials(creds);
            } catch (jcifsng212.CIFSException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * SMB2 Constructor
     *
     * @param smb1   		1 is use jcifs-1.3.17, 2 is jcifs-ng
     * @param domain        A domain name
     * @param user          A user name
     * @param pass          A password for user
     * @param ipc_signing_enforced true is use IpcSigningEnforced
     * @throws JcifsException
     */
    @SuppressWarnings("deprecation")
    public JcifsAuth(int smb_level, String domain, String user, String pass, boolean ipc_signing_enforced) {
        mSmbLevel = smb_level;
        mDomain = domain;
        mUserName = user;
        mUserPass = pass;
        if (isSmb212()) {
            try {
                Properties prop = new Properties();
                if (ipc_signing_enforced) prop.setProperty("jcifs.smb.client.ipcSigningEnforced", "true");
                else prop.setProperty("jcifs.smb.client.ipcSigningEnforced", "false");
                
                prop.setProperty("jcifs.smb.client.minVersion", "SMB202");
                prop.setProperty("jcifs.smb.client.maxVersion", "SMB300");

                jcifsng212.context.BaseContext bc = new jcifsng212.context.BaseContext(new jcifsng212.config.PropertyConfiguration(prop));
                jcifsng212.smb.NtlmPasswordAuthentication creds = new jcifsng212.smb.NtlmPasswordAuthentication(bc, domain, user, pass);
                mSmb212Auth = bc.withCredentials(creds);
            } catch (jcifsng212.CIFSException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * SMB2 Constructor
     *
     * @param smb1   		1 is use jcifs-1.3.17, 2 is jcifs-ng
     * @param domain        A domain name
     * @param user          A user name
     * @param pass          A password for user
     * @param ipc_signing_enforced true is use IpcSigningEnforced
     * @param use_smb2_nego true is use SMB2 Negotiation 
     * @throws JcifsException
     */
    @SuppressWarnings("deprecation")
    public JcifsAuth(int smb_level, String domain, String user, String pass, boolean ipc_signing_enforced, boolean use_smb2_nego) {
        mSmbLevel = smb_level;
        mDomain = domain;
        mUserName = user;
        mUserPass = pass;
        if (isSmb212()) {
            try {
                Properties prop = new Properties();
                if (ipc_signing_enforced) prop.setProperty("jcifs.smb.client.ipcSigningEnforced", "true");
                else prop.setProperty("jcifs.smb.client.ipcSigningEnforced", "false");
                
                if (use_smb2_nego) prop.setProperty("jcifs.smb.client.useSMB2Negotiation", "true");
                else prop.setProperty("jcifs.smb.client.useSMB2Negotiation", "false");
                
                prop.setProperty("jcifs.smb.client.minVersion", "SMB202");
                prop.setProperty("jcifs.smb.client.maxVersion", "SMB300");

                jcifsng212.context.BaseContext bc = new jcifsng212.context.BaseContext(new jcifsng212.config.PropertyConfiguration(prop));
                jcifsng212.smb.NtlmPasswordAuthentication creds = new jcifsng212.smb.NtlmPasswordAuthentication(bc, domain, user, pass);
                mSmb212Auth = bc.withCredentials(creds);
            } catch (jcifsng212.CIFSException e) {
                e.printStackTrace();
            }
        }
    }
    
    /**
     * SMB2 Constructor
     *
     * @param smb1   1 is use jcifs-1.3.17, 2 is jcifs-ng
     * @param domain               A domain name
     * @param user                 A user name
     * @param pass                 A password for user
     * @param ipc_signing_enforced true is use IpcSigningEnforced
     * @param min_version          min SMB version ("SMB1" or "SMB210")
     * @param max_version          max SMB version ("SMB1" or "SMB210")
     * @throws JcifsException
     */
    @SuppressWarnings("deprecation")
    public JcifsAuth(int smb_level, String domain, String user, String pass, boolean ipc_signing_enforced, String min_version, String max_version) {
        mSmbLevel = smb_level;
        mDomain = domain;
        mUserName = user;
        mUserPass = pass;
        if (isSmb212()) {
            try {
                Properties prop = new Properties();
                if (ipc_signing_enforced)
                    prop.setProperty("jcifs.smb.client.ipcSigningEnforced", "true");
                else prop.setProperty("jcifs.smb.client.ipcSigningEnforced", "false");
                prop.setProperty("jcifs.smb.client.useSMB2Negotiation", "true");
                prop.setProperty("jcifs.smb.client.minVersion", min_version);
                prop.setProperty("jcifs.smb.client.maxVersion", max_version);

                jcifsng212.context.BaseContext bc = new jcifsng212.context.BaseContext(new jcifsng212.config.PropertyConfiguration(prop));
                jcifsng212.smb.NtlmPasswordAuthentication creds = new jcifsng212.smb.NtlmPasswordAuthentication(bc, domain, user, pass);
                mSmb212Auth = bc.withCredentials(creds);
            } catch (jcifsng212.CIFSException e) {
                e.printStackTrace();
            }
        }
    }

    public int getSmbLevel() {
        return mSmbLevel;
    }

    public boolean isSmb1() {
        return mSmbLevel==JCIFS_FILE_SMB1;
    }

    public boolean isSmb212() {
        return mSmbLevel==JCIFS_FILE_SMB212;
    }

    public jcifs.smb.NtlmPasswordAuthentication getSmb1Auth() {
        return mSmb1Auth;
    }

    public jcifsng212.CIFSContext getSmb212Auth() {
        return mSmb212Auth;
    }

    public String getDomain() {
        return mDomain;
    }

    public String getUserName() {
        return mUserName;
    }

    public String getUserPass() {
        return mUserPass;
    }
}
