/*
 * +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 * + Copyright 2024. NHN Academy Corp. All rights reserved.
 * + * While every precaution has been taken in the preparation of this resource,  assumes no
 * + responsibility for errors or omissions, or for damages resulting from the use of the information
 * + contained herein
 * + No part of this resource may be reproduced, stored in a retrieval system, or transmitted, in any
 * + form or by any means, electronic, mechanical, photocopying, recording, or otherwise, without the
 * + prior written permission.
 * +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 */

package com.nhnacademy.http.context;

//TODO#3 - Context에 접근할 수 있도록 ContextHolder를 Singleton 구현 합니다.
//즉 Context가 web server 내에서 공유 됩니다.
// Holder 라는 것이 Context 를 잡고 있는 것 -> Holder 를 통해서 어디서든지 내부에 만들어진 ApplicationContext 에 접근 가능하다
public class ContextHolder {
    private static final Context context = new ApplicationContext(); // private + final

    public static synchronized ApplicationContext getApplicationContext() { // static 으로 선언하여 ContextHolder.ApplicationContext 로 접근 가능
        return (ApplicationContext) context;
    }
}
